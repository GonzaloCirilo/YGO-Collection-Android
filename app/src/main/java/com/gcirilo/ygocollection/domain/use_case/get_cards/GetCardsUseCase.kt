package com.gcirilo.ygocollection.domain.use_case.get_cards

import androidx.paging.PagingData
import com.gcirilo.ygocollection.domain.model.CardQuery
import com.gcirilo.ygocollection.domain.model.CardListing
import com.gcirilo.ygocollection.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val cardRepository: CardRepository,
) {

    operator fun invoke(cardQuery: CardQuery): Flow<PagingData<CardListing>> {
        return cardRepository.getCardsPaged(cardQuery)
    }
}