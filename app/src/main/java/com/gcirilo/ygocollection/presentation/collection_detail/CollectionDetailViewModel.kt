package com.gcirilo.ygocollection.presentation.collection_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gcirilo.ygocollection.domain.model.CollectionCards
import com.gcirilo.ygocollection.domain.use_case.delete_card_from_collection.DeleteCardFromCollectionUseCase
import com.gcirilo.ygocollection.domain.use_case.get_collection.GetCollectionUseCase
import com.gcirilo.ygocollection.presentation.navigation.Screen.CollectionDetailDestination.Args.CollectionId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(
    private val getCollectionUseCase: GetCollectionUseCase,
    private val deleteCardFromCollectionUseCase: DeleteCardFromCollectionUseCase,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    val collectionId = savedStateHandle.get<Long>(CollectionId.key) ?: 0L

    private val _collection = MutableStateFlow<CollectionCards?>(null)
    val collection = _collection.asStateFlow()

    init {
        viewModelScope.launch {
            getCollectionUseCase(collectionId).collect {
                _collection.value = it
            }
        }
    }

    fun onDeleteCard(cardId: Long) {
        viewModelScope.launch {
            deleteCardFromCollectionUseCase(collectionId, cardId)
        }
    }
}