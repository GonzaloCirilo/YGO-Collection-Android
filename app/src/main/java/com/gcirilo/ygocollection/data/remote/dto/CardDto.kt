package com.gcirilo.ygocollection.data.remote.dto

data class CardDto(
    val id: Long,
    val name: String,
    val type: String,
    val desc: String,
    val atk: Int?,
    val def: Int?,
    val level: Int?,
    val scale: Int?,
    val linkval: Int?,
    val linkMarkers: List<String>?,
    val race: String,
    val attribute: String?,
    val archetype: String?,
    val cardSets: List<CardSetDto>,
    val cardImages: List<CardImageDto>?,
    val cardPrices: List<CardPriceDto>
)
