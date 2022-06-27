package com.gcirilo.ygocollection.domain.repository

import androidx.paging.PagingData
import com.gcirilo.ygocollection.domain.model.CardQuery
import com.gcirilo.ygocollection.domain.model.Card
import com.gcirilo.ygocollection.domain.model.CardListing
import com.gcirilo.ygocollection.util.Resource
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    fun getCardsPaged(cardQuery: CardQuery): Flow<PagingData<CardListing>>

    suspend fun getCard(id: Long): Flow<Resource<Card>>
}