package com.gcirilo.ygocollection.domain.model

data class Card(
    val id: Long,
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
)
