package com.gcirilo.ygocollection.domain.model

data class CollectionCards(
    val id: Long,
    val name: String,
    val cards: List<Card>,
)
