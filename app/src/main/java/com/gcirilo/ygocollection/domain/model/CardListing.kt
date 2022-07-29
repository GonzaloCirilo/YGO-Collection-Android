package com.gcirilo.ygocollection.domain.model

data class CardListing(
    val id: Long,
    var name: String,
    var type: String,
    val imageUrl: String = "",
    val atk: Int?,
    val def: Int?,
    val level: Int?,
    val scale: Int?,
    val linkval: Int?,
)
