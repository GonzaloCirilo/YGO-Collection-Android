package com.gcirilo.ygocollection.presentation.card_list

sealed class CardListEvent{
    object Refresh: CardListEvent()
    data class OnCardQueryChange(val query: CardQueryItemType): CardListEvent()
    data class OnFilterQueryChange(val query: String): CardListEvent()
    class onCardSelected(val cardId: Long): CardListEvent()
}
