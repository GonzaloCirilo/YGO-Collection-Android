package com.gcirilo.ygocollection.presentation.card_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.gcirilo.ygocollection.domain.model.CardListing
import com.gcirilo.ygocollection.presentation.card_list.components.*
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme
import com.gcirilo.ygocollection.presentation.card_list.CardListViewModel.NavTarget.*
import com.gcirilo.ygocollection.presentation.navigation.Screen.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun CardListScreen(navController: NavController){
    val viewModel: CardListViewModel = hiltViewModel()
    val state: CardListState by viewModel.state
    val cards = viewModel.getPagedCards.collectAsLazyPagingItems()
    val localLifecycleOwner = LocalLifecycleOwner.current
    CardListScreenContent(cards = cards, state = state, onEvent = { viewModel.onEvent(it) })

    LaunchedEffect(key1 = Unit){
        localLifecycleOwner.lifecycleScope.launch{
            localLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.navEvent.collect{
                    when(it){
                        is CardDetail -> navController.navigate(
                            CardDetailDestination.createRoute(
                                mapOf(
                                    CardDetailDestination.Args.CardId to it.cardId
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CardListScreenContent(
    cards: LazyPagingItems<CardListing>,
    state: CardListState = CardListState(),
    onEvent: (CardListEvent) -> Unit = {}
){
    var filterSurface by remember {
        mutableStateOf(false)
    }

    Column {
        CardSearchBar(
            cardQuery = state.query,
            filterSearch = state.filterQuery,
            onFilterFocusChange = { filterSurface = it },
            onEvent = onEvent,
        )
        CardQueryItemsLayout(state = state, onEvent = onEvent)
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 2.dp),
        ){
            FilterOptionsList(
                state = state,
                filterSurface = filterSurface,
                onEvent = { query, showFilterSurface ->
                    filterSurface = showFilterSurface
                    onEvent(query)
                }
            )
            CardListGrid(cards = cards, onCardSelected = { onEvent(CardListEvent.OnCardSelected(it)) })
        }

    }
}

@Preview
@ExperimentalMaterialApi
@Composable
fun PreviewCardListScreenContent() {
    val ordersFlow = flowOf(PagingData.from(listOf<CardListing>()))
    val pagingItems = ordersFlow.collectAsLazyPagingItems()
    YGOCollectionTheme {
        CardListScreenContent(cards = pagingItems)
    }
}