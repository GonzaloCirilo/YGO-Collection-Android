package com.gcirilo.ygocollection.presentation.card_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gcirilo.ygocollection.domain.model.Card
import com.gcirilo.ygocollection.domain.use_case.get_card.GetCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import com.gcirilo.ygocollection.presentation.navigation.Screen.*
import com.gcirilo.ygocollection.util.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCardUseCase: GetCardUseCase
): ViewModel() {

    val id = savedStateHandle.getStateFlow(CardDetailDestination.Args.CardId.key, 0L)

    private var _card = MutableStateFlow<Card?>(null)
    val card: StateFlow<Card?> = _card

    private var _shouldShowLoadingIndicator = MutableStateFlow<Boolean>(false)
    val shouldShowLoadingIndicator: StateFlow<Boolean> = _shouldShowLoadingIndicator

    init {
        getCardInfo()
    }

    private fun getCardInfo () {
        viewModelScope.launch {
            id.collect { id ->
                if (id != 0L) {
                    getCardUseCase(id).collect { response ->
                        when(response) {
                            is Resource.Loading -> _shouldShowLoadingIndicator.update { true }
                            is Resource.Error -> {
                                _shouldShowLoadingIndicator.update { false }
                            }
                            is Resource.Success -> {
                                _shouldShowLoadingIndicator.update { false }
                                _card.update { response.responseData }
                            }
                        }
                    }
                }
            }
        }
    }


}