package com.gcirilo.ygocollection.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.gcirilo.ygocollection.data.local.CardEntity
import com.gcirilo.ygocollection.data.local.CardQuery
import com.gcirilo.ygocollection.data.local.YGOCollectionDatabase
import com.gcirilo.ygocollection.data.mapper.toCardEntity
import com.gcirilo.ygocollection.data.mapper.toCardListing
import com.gcirilo.ygocollection.data.remote.YGOCardService
import com.gcirilo.ygocollection.domain.model.CardListing
import com.gcirilo.ygocollection.domain.repository.CardRepository
import com.gcirilo.ygocollection.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
class CardRepositoryImpl @Inject constructor(
    private val cardService: YGOCardService,
    private val db: YGOCollectionDatabase,
) : CardRepository {

    private val cardDao = db.cardsDao

    override suspend fun getCards(fetchFromRemote: Boolean, query: String): Flow<Resource<List<CardListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localCards = cardDao.searchCards(query)
            emit(Resource.Success(localCards.map { it.toCardListing() }))

            val isCardsEmpty = localCards.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isCardsEmpty && !fetchFromRemote

            if(shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteCards = try {
                val response = cardService.getCards(emptyMap())
                response.data
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.localizedMessage ?:""))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.localizedMessage ?:""))
                null
            }

            remoteCards?.let { cards ->
                emit(Resource.Success(cards.map { it.toCardEntity().toCardListing() }))
                emit(Resource.Loading(false))
            }
        }
    }

    override fun getCardsPaged(cardQuery: CardQuery): Pager<Int, CardEntity> {
        return Pager(
            config = PagingConfig(pageSize = cardQuery.pageSize, initialLoadSize = cardQuery.pageSize),
            remoteMediator = CardRemoteMediator(cardService = cardService, database = db, cardQuery = cardQuery)
        ){
            db.cardsDao.searchCardsPaged(cardQuery.toSQLiteQuery())
        }
    }
}