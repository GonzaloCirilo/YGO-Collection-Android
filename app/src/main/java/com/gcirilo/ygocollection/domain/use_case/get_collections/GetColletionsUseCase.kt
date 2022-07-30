package com.gcirilo.ygocollection.domain.use_case.get_collections

import com.gcirilo.ygocollection.domain.model.Collection
import com.gcirilo.ygocollection.domain.repository.CollectionRepository
import javax.inject.Inject

class GetColletionsUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
){
    suspend operator fun invoke(): List<Collection> {
        return collectionRepository.getCollections()
    }
}