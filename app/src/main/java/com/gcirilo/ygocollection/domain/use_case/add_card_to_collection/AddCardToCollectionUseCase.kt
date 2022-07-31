package com.gcirilo.ygocollection.domain.use_case.add_card_to_collection

import com.gcirilo.ygocollection.domain.repository.CollectionRepository
import javax.inject.Inject

class AddCardToCollectionUseCase @Inject constructor(
    private val cardCollectionRepository: CollectionRepository
) {
    suspend operator fun invoke(collectionId: Long, cardId: Long) {
        cardCollectionRepository.addCardsToCollection(collectionId, cardId)
    }
}