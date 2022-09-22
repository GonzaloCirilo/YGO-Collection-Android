package com.gcirilo.ygocollection.domain.use_case.get_collection

import com.gcirilo.ygocollection.domain.model.CollectionCards
import com.gcirilo.ygocollection.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {

    suspend operator fun invoke(id: Long): Flow<CollectionCards> {
        return collectionRepository.getCollection(id)
    }
}