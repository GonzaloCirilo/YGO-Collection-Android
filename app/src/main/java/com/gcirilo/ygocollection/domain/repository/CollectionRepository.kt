package com.gcirilo.ygocollection.domain.repository

import com.gcirilo.ygocollection.domain.model.Collection
import com.gcirilo.ygocollection.domain.model.CollectionCards
import com.gcirilo.ygocollection.domain.model.CollectionForm
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    fun getCollections(): Flow<List<Collection>>

    suspend fun deleteCollection(collection: Collection)

    suspend fun getCollection(id: Long): Flow<CollectionCards>

    suspend fun saveCollection(collection: CollectionForm)

    suspend fun addCardsToCollection(collectionId: Long, vararg cardIds: Long)

    suspend fun removeCardsFromCollection(collectionId: Long, vararg cardIds: Long)
}