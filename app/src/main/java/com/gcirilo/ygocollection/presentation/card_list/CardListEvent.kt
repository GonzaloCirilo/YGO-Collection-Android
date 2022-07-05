package com.gcirilo.ygocollection.presentation.card_list

sealed class CardListEvent{
    object Refresh: CardListEvent()
    data class OnCardQueryChange(val query: CardQueryItemType): CardListEvent()
    data class OnArchetypeChange(val query: String): CardListEvent()

}
