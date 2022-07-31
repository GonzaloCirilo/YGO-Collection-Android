package com.gcirilo.ygocollection.domain.use_case.get_collections

import com.gcirilo.ygocollection.domain.model.Collection
import com.gcirilo.ygocollection.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionsUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
){
    operator fun invoke(): Flow<List<Collection>> {
        return collectionRepository.getCollections()
    }
}