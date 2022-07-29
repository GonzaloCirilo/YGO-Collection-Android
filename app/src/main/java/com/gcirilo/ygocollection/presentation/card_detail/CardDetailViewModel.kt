package com.gcirilo.ygocollection.presentation.card_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.gcirilo.ygocollection.presentation.navigation.Screen.*
import javax.inject.Inject

@HiltViewModel
class CardDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val id = savedStateHandle.getStateFlow(CardDetailDestination.Args.CardId.key, 0L)


}