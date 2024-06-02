package com.gcirilo.ygocollection.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.gcirilo.ygocollection.data.local.entity.CardCollectionEntity
import com.gcirilo.ygocollection.data.local.entity.CardIdAndCollectionId
import com.gcirilo.ygocollection.data.local.entity.CardIdAndCollectionIdDelete

@Dao
interface CardCollectionDao {

    @Delete(entity = CardCollectionEntity::class)
    suspend fun deleteByCardIdAndCollectionId(vararg cardCollection: CardIdAndCollectionIdDelete)

    @Upsert(entity = CardCollectionEntity::class)
    suspend fun insertByCardIdAndCollectionId(vararg cardCollection: CardIdAndCollectionId)

    @Query("""
        SELECT * FROM card_collection_entity
        WHERE cardId LIKE :cardId AND collectionId LIKE :collectionId
    """)
    suspend fun getCardCollection(cardId: Long, collectionId: Long): CardCollectionEntity?
}