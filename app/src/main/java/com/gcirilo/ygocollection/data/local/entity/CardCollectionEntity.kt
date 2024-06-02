package com.gcirilo.ygocollection.data.local.entity

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
    val quantity: Int
)

data class CardIdAndCollectionId (
    val id: Long? = null,
    val cardId: Long,
    val collectionId: Long,
    val quantity: Int
)

data class CardIdAndCollectionIdDelete (
    val cardId: Long,
    val collectionId: Long,
)