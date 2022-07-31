package com.gcirilo.ygocollection.domain.use_case.add_collection

import com.gcirilo.ygocollection.domain.model.CollectionForm
import com.gcirilo.ygocollection.domain.repository.CollectionRepository
import javax.inject.Inject


class AddCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {

    suspend operator fun invoke(collection: CollectionForm) {
        collectionRepository.saveCollection(collection)
    }
}