package com.gcirilo.ygocollection.domain.use_case.get_cards

import androidx.paging.PagingData
import androidx.paging.map
import com.gcirilo.ygocollection.data.local.CardQuery
import com.gcirilo.ygocollection.data.mapper.toCardListing
import com.gcirilo.ygocollection.domain.model.CardListing
import com.gcirilo.ygocollection.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val cardRepository: CardRepository,
) {

    operator fun invoke(cardQuery: CardQuery): Flow<PagingData<CardListing>> {
        return cardRepository.getCardsPaged(cardQuery).flow.map { pagingData ->
            pagingData.map { it.toCardListing() }
        }
    }
}