package com.gcirilo.ygocollection.domain.use_case.delete_card_from_collection

import com.gcirilo.ygocollection.domain.repository.CollectionRepository
import javax.inject.Inject

class DeleteCardFromCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {

    suspend operator fun invoke(id: Long, vararg cardIds: Long) {
        collectionRepository.removeCardsFromCollection(id, *cardIds)
    }
}