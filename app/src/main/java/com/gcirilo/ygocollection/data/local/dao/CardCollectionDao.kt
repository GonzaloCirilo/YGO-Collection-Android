package com.gcirilo.ygocollection.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.gcirilo.ygocollection.data.local.CardCollectionEntity
import com.gcirilo.ygocollection.data.local.CardIdAndCollectionId

@Dao
interface CardCollectionDao {

    @Delete(entity = CardCollectionEntity::class)
    suspend fun deleteByCardIdAndCollectionId(vararg cardCollection: CardIdAndCollectionId)

    @Insert(entity = CardCollectionEntity::class)
    suspend fun insertByCardIdAndCollectionId(vararg cardCollection: CardIdAndCollectionId)
}