package com.gcirilo.ygocollection.domain.repository

import com.gcirilo.ygocollection.domain.model.Collection
import com.gcirilo.ygocollection.domain.model.CollectionCards

interface CollectionRepository {
    suspend fun getCollections(): List<Collection>

    suspend fun deleteCollection(collection: Collection)

    suspend fun getCollection(id: Long): CollectionCards

    suspend fun saveCollection(collection: Collection)

    suspend fun addCardsToCollection(collectionId: Long, vararg cardIds: Long)

    suspend fun removeCardsFromCollection(collectionId: Long, vararg cardIds: Long)
}