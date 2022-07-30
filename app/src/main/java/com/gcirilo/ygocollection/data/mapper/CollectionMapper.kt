package com.gcirilo.ygocollection.data.mapper

import com.gcirilo.ygocollection.data.local.CollectionAndAllCards
import com.gcirilo.ygocollection.data.local.CollectionEntity
import com.gcirilo.ygocollection.domain.model.Collection
import com.gcirilo.ygocollection.domain.model.CollectionCards

fun CollectionEntity.toCollection(): Collection {
    return Collection(id = id, name = name, 0)
}

fun CollectionAndAllCards.toCollectionCards(): CollectionCards {
    return CollectionCards(
        id = collectionEntity.id,
        name = collectionEntity.name,
        cards = cards.map { it.toCard() }
    )
}

fun Collection.toCollectionEntity(): CollectionEntity {
    return CollectionEntity(id, name)
}
