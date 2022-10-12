package com.gcirilo.ygocollection.data.mapper

import com.gcirilo.ygocollection.data.local.CollectionAndAllCards
import com.gcirilo.ygocollection.data.local.CollectionEntity
import com.gcirilo.ygocollection.domain.model.Collection
import com.gcirilo.ygocollection.domain.model.CollectionCards
import com.gcirilo.ygocollection.domain.model.CollectionForm

fun CollectionAndAllCards.toCollection(): Collection {
    val topCards = if(cards.size < 4)
        cards.take(1)
    else
        cards.take(4)
    return Collection(
        id = collectionEntity.id,
        name = collectionEntity.name,
        // TODO consider value object for ArtworkImage
        artworkUrls = topCards.map { it.imageUrl.replace("cards", "cards_cropped") },
        cardCount = cards.size.toLong(),
    )
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

fun CollectionCards.toCollectionForm(): CollectionForm {
    return CollectionForm(id, name)
}

fun CollectionForm.toCollectionEntity(): CollectionEntity {
    return CollectionEntity(id, name)
}
