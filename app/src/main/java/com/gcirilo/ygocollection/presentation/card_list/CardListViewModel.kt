package com.gcirilo.ygocollection.presentation.card_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.gcirilo.ygocollection.data.local.CardQuery
import com.gcirilo.ygocollection.domain.use_case.get_cards.GetCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardListViewModel @Inject constructor(
    private val getCards: GetCardsUseCase,
): ViewModel() {

    fun getPagedCards(query: CardQuery) = getCards.invoke(query).cachedIn(viewModelScope)

}