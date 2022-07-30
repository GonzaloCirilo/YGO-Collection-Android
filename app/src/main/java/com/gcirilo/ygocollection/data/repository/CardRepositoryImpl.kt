package com.gcirilo.ygocollection.data.repository

import androidx.paging.*
import com.gcirilo.ygocollection.domain.model.CardQuery
import com.gcirilo.ygocollection.data.local.YGOCollectionDatabase
import com.gcirilo.ygocollection.data.mapper.toCard
import com.gcirilo.ygocollection.data.mapper.toCardEntity
import com.gcirilo.ygocollection.data.mapper.toCardListing
import com.gcirilo.ygocollection.data.remote.YGOCardService
import com.gcirilo.ygocollection.domain.model.Card
import com.gcirilo.ygocollection.domain.model.CardListing
import com.gcirilo.ygocollection.domain.repository.CardRepository
import com.gcirilo.ygocollection.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

    override suspend fun getCard(id: Long): Flow<Resource<Card>> {
        return flow {
            emit(Resource.Loading(true))
            val localCard = cardDao.getCard(id)
            emit(Resource.Success(localCard.toCard()))

            val remoteCard = try {
                val response = cardService.getCard(id)
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
            remoteCard?.let { card ->
                cardDao.insertCards(listOf(card.first().toCardEntity()))
                emit(Resource.Success(
                    data = cardDao.getCard(id).toCard()
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    override fun getCardsPaged(cardQuery: CardQuery): Flow<PagingData<CardListing>> {
        return Pager(
            config = PagingConfig(pageSize = cardQuery.pageSize, initialLoadSize = cardQuery.pageSize),
            remoteMediator = CardRemoteMediator(cardService = cardService, database = db, cardQuery = cardQuery)
        ){
            db.cardsDao.searchCardsPaged(cardQuery.toSQLiteQuery())
        }.flow.map { pagingData->
            pagingData.map {
                it.toCardListing()
            }
        }
    }
}