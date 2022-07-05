package com.gcirilo.ygocollection.presentation.card_list

import com.gcirilo.ygocollection.domain.model.CardQuery

sealed class CardQueryItemType(val value: String) {
    class ArchetypeFilter(value: String): CardQueryItemType(value)
    class NameSearch(value: String): CardQueryItemType(value)
}

fun CardQuery.toCardQueryItemTypes(): List<CardQueryItemType> {
    val list = mutableListOf<CardQueryItemType>()
    this.archetype?.let {
        list.add(CardQueryItemType.ArchetypeFilter(it))
    }
    return list
}