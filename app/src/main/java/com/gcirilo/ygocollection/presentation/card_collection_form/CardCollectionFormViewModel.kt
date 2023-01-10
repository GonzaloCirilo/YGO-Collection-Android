package com.gcirilo.ygocollection.presentation.card_collection_form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gcirilo.ygocollection.domain.model.Collection
import com.gcirilo.ygocollection.domain.use_case.add_card_to_collection.AddCardToCollectionUseCase
import com.gcirilo.ygocollection.domain.use_case.get_collections.GetCollectionsUseCase
import com.gcirilo.ygocollection.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardCollectionFormViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val addCardToCollectionUseCase: AddCardToCollectionUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val cardId = savedStateHandle.getStateFlow(Screen.CardCollectionFormDestination.Args.CardId.key, 0L)

    private val _selectedCollectionId = MutableStateFlow(0L)

    private var _collections = MutableStateFlow<List<Collection>>(emptyList())
    val collections: StateFlow<List<Collection>> = _collections

    init {
        viewModelScope.launch {
            getCollectionsUseCase().collect {
                _collections.value = it
            }
        }
    }

    fun onCollectionSelected(id: Long) {
        _selectedCollectionId.value = id
    }

    fun onSaveToCollection() {
        if(_selectedCollectionId.value != 0L){
            viewModelScope.launch {
                addCardToCollectionUseCase(_selectedCollectionId.value, cardId.first())
            }
        }
    }
}