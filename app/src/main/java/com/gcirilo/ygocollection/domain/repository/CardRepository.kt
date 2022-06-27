package com.gcirilo.ygocollection.domain.repository

import androidx.paging.Pager
import com.gcirilo.ygocollection.data.local.CardEntity
import com.gcirilo.ygocollection.data.local.CardQuery
import com.gcirilo.ygocollection.domain.model.CardListing
import com.gcirilo.ygocollection.util.Resource
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    suspend fun getCards(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CardListing>>>

    fun getCardsPaged(cardQuery: CardQuery): Pager<Int, CardEntity>
}