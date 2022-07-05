package com.gcirilo.ygocollection.presentation.card_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.gcirilo.ygocollection.domain.use_case.get_archetypes.GetArchetypesUseCase
import com.gcirilo.ygocollection.domain.use_case.get_cards.GetCardsUseCase
import com.gcirilo.ygocollection.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardListViewModel @Inject constructor(
    private val getCards: GetCardsUseCase,
    private val retrieveArchetypes: GetArchetypesUseCase
): ViewModel() {

    private var _state = mutableStateOf(CardListState())
    val state: State<CardListState> = _state

    var getPagedCards = getCards(_state.value.query).cachedIn(viewModelScope)
        private set

    private var searchArchetypeJob: Job? = null
    private var searchCardQueryJob: Job? = null
    private var searchCardQueryDelay = 0L

    init {
        viewModelScope.launch {
            getArchetypes()
        }
    }

    fun onEvent(event: CardListEvent) {
        searchCardQueryDelay = 0L
        when(event){
            is CardListEvent.OnCardQueryChange -> {
                _state.value = _state.value.copy(
                    query = when(event.query){
                        is CardQueryItemType.ArchetypeFilter -> {
                            _state.value.query.copy(archetype = event.query.value)
                        }
                        is CardQueryItemType.NameSearch -> {
                            searchCardQueryDelay = 750L // delay for name to avoid calling API in short period of time
                            _state.value.query.copy(cardName = event.query.value)
                        }
                    },
                    filterQuery = if(event.query is CardQueryItemType.NameSearch)
                        _state.value.filterQuery
                    else
                        "",
                    isLoading = true,
                )

                if(event.query !is CardQueryItemType.NameSearch){ // if is not a name search
                    getArchetypes() // reset the filter Query Search
                }
                searchCardQueryJob?.cancel()
                searchCardQueryJob = viewModelScope.launch {
                    delay(searchCardQueryDelay)
                    getPagedCards = getCards(_state.value.query).cachedIn(viewModelScope)
                    _state.value = _state.value.copy(isLoading = false)
                }
            }
            is CardListEvent.OnArchetypeChange -> {
                _state.value = _state.value.copy(filterQuery = event.query)
                searchArchetypeJob?.cancel()
                searchArchetypeJob = viewModelScope.launch {
                    delay(500L)
                    getArchetypes()
                }
            }
            is CardListEvent.Refresh -> {}
        }
    }

    private fun getArchetypes(
        query: String = state.value.filterQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            retrieveArchetypes(query, fetchFromRemote).collect { result ->
                when(result){
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = result.isLoading)
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(archetypes = result.responseData.orEmpty())
                    }
                    is Resource.Error -> {
                        Log.d(CardListViewModel::class.java.simpleName, result.message.orEmpty())
                    }
                }

            }
        }

    }
}