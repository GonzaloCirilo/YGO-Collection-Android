package com.gcirilo.ygocollection.domain.use_case.get_card_collection

import com.gcirilo.ygocollection.domain.model.CardCollection
import com.gcirilo.ygocollection.domain.repository.CollectionRepository
import javax.inject.Inject

class GetCardCollectionUseCase @Inject constructor(
    private val cardCollectionRepository: CollectionRepository
) {
    suspend operator fun invoke(collectionId: Long, cardId: Long): CardCollection? {
        return cardCollectionRepository.getCardCollection(cardId, collectionId)
    }
}