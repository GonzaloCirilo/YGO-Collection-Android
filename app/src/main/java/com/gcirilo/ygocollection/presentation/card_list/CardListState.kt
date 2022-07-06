package com.gcirilo.ygocollection.presentation.card_list

import com.gcirilo.ygocollection.domain.model.Archetype
import com.gcirilo.ygocollection.domain.model.CardQuery
import com.gcirilo.ygocollection.domain.model.CardRace

data class CardListState(
    val archetypes: List<Archetype> = emptyList(),
    val filterQuery: String = "",
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val query: CardQuery = CardQuery(),
    val monsterCardRaces: List<CardRace> = CardRace.monsterCardRaces(),
    val spellTrapCardRaces: List<CardRace> = CardRace.spellTrapCardRaces(),
)
