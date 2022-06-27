package com.gcirilo.ygocollection.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_entity")
data class CardEntity (
    @PrimaryKey val id: Long,
    val name: String,
    val type: String,
    val desc: String,
    val atk: Int?,
    val def: Int?,
    val level: Int?,
    val scale: Int?,
    val linkval: Int?,
    val linkMarkers: String?,
    val race: String,
    val attribute: String?,
    val archetype: String?,
    val avgTcgplayerPrice: Double,
    val avgEbayPrice: Double,
    val avgAmazonPrice: Double,
    val imageUrl: String,
    val smallImageUrl: String,
)