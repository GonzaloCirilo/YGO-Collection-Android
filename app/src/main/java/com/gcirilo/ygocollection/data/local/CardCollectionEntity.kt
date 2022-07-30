package com.gcirilo.ygocollection.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "card_collection_entity",
    indices = [
        Index("cardId"), Index("collectionId")
    ]
)
data class CardCollectionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val cardId: Long,
    val collectionId: Long,
)

data class CardIdAndCollectionId (
    val cardId: Long,
    val collectionId: Long
)