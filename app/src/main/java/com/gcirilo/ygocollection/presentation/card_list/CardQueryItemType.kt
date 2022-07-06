package com.gcirilo.ygocollection.presentation.card_list

import com.gcirilo.ygocollection.domain.model.CardQuery

sealed class CardQueryItemType(val stringValue: String?, val label: String? = null) {
    class ArchetypeFilter(value: String?): CardQueryItemType(value)
    class NameSearch(value: String): CardQueryItemType(value)
    class RaceFilter(value: String, label: String, val shouldBeRemoved: Boolean): CardQueryItemType(value, label)
}

fun CardQuery.toCardQueryItemTypes(): List<CardQueryItemType> {
    val list = mutableListOf<CardQueryItemType>()
    this.archetype?.let {
        list.add(CardQueryItemType.ArchetypeFilter(it))
    }
    this.races?.let { cardRaces ->
        cardRaces.forEach { cardRace ->
            list.add(CardQueryItemType.RaceFilter(cardRace.name, cardRace.toString(),false))
        }
    }

    return list
}