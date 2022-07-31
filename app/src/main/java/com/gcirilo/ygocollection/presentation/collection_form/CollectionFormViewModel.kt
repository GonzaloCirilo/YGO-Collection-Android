package com.gcirilo.ygocollection.presentation.collection_form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gcirilo.ygocollection.data.mapper.toCollectionForm
import com.gcirilo.ygocollection.domain.model.CollectionForm
import com.gcirilo.ygocollection.domain.use_case.add_collection.AddCollectionUseCase
import com.gcirilo.ygocollection.domain.use_case.get_collection.GetCollectionUseCase
import com.gcirilo.ygocollection.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CollectionFormViewModel @Inject constructor(
    private val addCollectionUseCase: AddCollectionUseCase,
    private val getCollectionUseCase: GetCollectionUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val collectionInitialValue = CollectionForm(0L, "")
    private val _collection = MutableStateFlow(collectionInitialValue)
    val collection: StateFlow<CollectionForm> = _collection

    init {
        viewModelScope.launch{
            savedStateHandle.getStateFlow(Screen.CollectionFormDestination.Args.CollectionId.key, 0L).collect{
                if(it != 0L) {
                    val result = getCollectionUseCase(it)
                    _collection.update { result.toCollectionForm() }
                }
            }
        }
    }


    fun onNameChange(newValue: String) {
        _collection.update { it.copy(name = newValue) }
    }

    fun saveCollection() {
        viewModelScope.launch {
            addCollectionUseCase(_collection.value)
        }
    }
}