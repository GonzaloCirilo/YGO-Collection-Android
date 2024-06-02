package com.gcirilo.ygocollection.domain.model

data class CardCollection(
    val id: Long?,
    val cardId: Long,
    val collectionId: Long,
    val quantity: Int,
)
