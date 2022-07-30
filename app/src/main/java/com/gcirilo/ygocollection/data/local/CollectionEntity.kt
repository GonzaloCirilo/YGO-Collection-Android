package com.gcirilo.ygocollection.data.local

import androidx.room.*

@Entity(tableName = "collection_entity")
data class CollectionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
)

// POJO

data class CollectionAndAllCards(
    @Embedded
    val collectionEntity: CollectionEntity,
    @Relation(
        parentColumn = "id",
        entity = CardEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = CardCollectionEntity::class,
            parentColumn = "collectionId",
            entityColumn = "cardId"
        )
    )
    val cards: List<CardEntity>
)
