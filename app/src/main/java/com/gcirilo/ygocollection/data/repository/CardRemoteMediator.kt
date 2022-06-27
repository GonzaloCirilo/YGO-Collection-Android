package com.gcirilo.ygocollection.data.repository

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.gcirilo.ygocollection.data.local.CardEntity
import com.gcirilo.ygocollection.data.local.CardQuery
import com.gcirilo.ygocollection.data.local.RemoteKey
import com.gcirilo.ygocollection.data.local.YGOCollectionDatabase
import com.gcirilo.ygocollection.data.mapper.toCardEntity
import com.gcirilo.ygocollection.data.remote.YGOCardService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class CardRemoteMediator @Inject constructor(
    private val cardService: YGOCardService,
    private val database: YGOCollectionDatabase,
    private val cardQuery: CardQuery,
): RemoteMediator<Int, CardEntity>() {

    private val STARTING_PAGE_INDEX = 1

    private var pageSize = 20

    private val cardDao by lazy { database.cardsDao }

    private val remoteKeyDao by lazy { database.remoteKeysDao() }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CardEntity>): RemoteKey? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        val lastPage = state.pages.lastOrNull { it.data.isNotEmpty() }
        return lastPage?.data?.lastOrNull()?.let { card ->
            // Get the remote keys of the last item retrieved from the last page
            remoteKeyDao.remoteKeyByCardIdAndQuery(card.id, cardQuery.toRemoteKeyString())
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CardEntity>): RemoteKey? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        val firstPage = state.pages.firstOrNull { it.data.isNotEmpty() }
        return firstPage?.data?.firstOrNull()?.let { card ->
            // Get the remote keys of the first items retrieved
            remoteKeyDao.remoteKeyByCardIdAndQuery(card.id, cardQuery.toRemoteKeyString())
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CardEntity>
    ): RemoteKey? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { cardId ->
                remoteKeyDao.remoteKeyByCardIdAndQuery(cardId, cardQuery.toRemoteKeyString())
            }
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CardEntity>
    ): MediatorResult {
        return try {
            val currentPageKey = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    Log.d(CardRemoteMediator::class.java.simpleName, "REFRESH")
                    remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    // If remoteKeys is null, that means the refresh result is not in the database yet.
                    // We can return Success with endOfPaginationReached = false because Paging
                    // will call this method again if RemoteKeys becomes non-null.
                    // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                    // the end of pagination for append.
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    Log.d(CardRemoteMediator::class.java.simpleName, "APPEND")
                    nextKey
                }
            }

            // retrieve cards
            val response = cardService.getCards(cardQuery.toQueryMap(currentPageKey))
            val endOfPaginationReached = response.meta?.pagesRemaining == 0L

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeysWithQuery(cardQuery.toRemoteKeyString())
                }

                val prevKey = if (currentPageKey == STARTING_PAGE_INDEX) null else currentPageKey - 1
                val nextKey = if (endOfPaginationReached) null else currentPageKey + 1
                val keys = response.data.map {
                    RemoteKey(cardId = it.id, prevKey = prevKey, nextKey = nextKey, query = cardQuery.toRemoteKeyString())
                }
                cardDao.insertCards(response.data.map { it.toCardEntity() })
                remoteKeyDao.insertAll(keys)
            }
            MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private fun offsetFromPage(page: Int): Int {
        return (page - 1) * pageSize
    }

}