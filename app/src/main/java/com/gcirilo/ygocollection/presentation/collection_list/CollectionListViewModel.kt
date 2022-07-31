package com.gcirilo.ygocollection.presentation.collection_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gcirilo.ygocollection.domain.model.Collection
import com.gcirilo.ygocollection.domain.use_case.get_collections.GetCollectionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionListViewModel @Inject constructor(
    getCollectionsUseCase: GetCollectionsUseCase
): ViewModel() {

    private var _collections = MutableStateFlow<List<Collection>>(emptyList())
    val collections: StateFlow<List<Collection>> = _collections

    init {
        viewModelScope.launch {
            getCollectionsUseCase().collect {
                _collections.value = it
            }
        }
    }
}