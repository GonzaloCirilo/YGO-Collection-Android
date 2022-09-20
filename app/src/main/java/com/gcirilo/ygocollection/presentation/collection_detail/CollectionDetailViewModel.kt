package com.gcirilo.ygocollection.presentation.collection_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gcirilo.ygocollection.domain.model.CollectionCards
import com.gcirilo.ygocollection.domain.use_case.get_collection.GetCollectionUseCase
import com.gcirilo.ygocollection.presentation.navigation.Screen.CollectionDetailDestination.Args.CollectionId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(
    private val getCollectionUseCase: GetCollectionUseCase,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    val collectionId = savedStateHandle.getStateFlow(CollectionId.key, 0L)

    val collection: StateFlow<CollectionCards?> = collectionId.transform { id ->
        emit(getCollectionUseCase(id))
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)
}