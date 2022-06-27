package com.gcirilo.ygocollection.data.mapper

import com.gcirilo.ygocollection.data.local.CardEntity
import com.gcirilo.ygocollection.data.remote.dto.CardDto
import com.gcirilo.ygocollection.domain.model.Card
import com.gcirilo.ygocollection.domain.model.CardListing

fun CardEntity.toCard(): Card {
    return Card(
        id, name, type, desc, atk, def, level, scale, linkval, linkMarkers, race, attribute,
        archetype, avgTcgplayerPrice, avgEbayPrice, avgAmazonPrice, imageUrl
    )
}

fun CardEntity.toCardListing(): CardListing{
    return CardListing(name, type, imageUrl, atk, def, level, scale, linkval)
}

fun CardDto.toCardEntity(): CardEntity {
    return CardEntity(
        id = id,
        name = name,
        type = type,
        desc = desc,
        race = race,
        atk = atk,
        def = def,
        level = level,
        scale = scale,
        linkval = linkval,
        linkMarkers = linkMarkers?.joinToString(separator = "|"),
        archetype = archetype,
        attribute = attribute,
        avgEbayPrice = cardPrices.map { it.ebayPrice.toDouble() }.average(),
        avgAmazonPrice = cardPrices.map { it.amazonPrice.toDouble() }.average(),
        avgTcgplayerPrice = cardPrices.map { it.tcgplayerPrice.toDouble() }.average(),
        imageUrl = cardImages?.reduce { x, _ -> x }?.imageUrl ?: "",
        smallImageUrl = cardImages?.reduce{ x, _ -> x}?.imageUrlSmall ?: ""
    )
}