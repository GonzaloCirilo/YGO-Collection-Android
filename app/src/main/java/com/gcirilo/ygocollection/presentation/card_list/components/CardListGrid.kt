package com.gcirilo.ygocollection.presentation.card_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.gcirilo.ygocollection.domain.model.CardListing
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun CardListGrid(cards: LazyPagingItems<CardListing>, onCardSelected: (Long) -> Unit = {}){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        lazyItems(cards){ card ->
            card?.let {
                CardListItem(card = card, onCardSelected)
            }
        }
    }
}

@Composable
fun CardListGrid(cards: List<CardListing>, onCardSelected: (Long) -> Unit = {}){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(cards){ card ->
            card.let {
                CardListItem(card = card, onCardSelected)
            }
        }
    }
}

inline fun <T : Any> LazyGridScope.lazyItems(
    items: LazyPagingItems<T>,
    crossinline itemContent: @Composable LazyGridItemScope.(item: T?) -> Unit
) {
    items(count = items.itemCount) { index ->
        itemContent(items[index])
    }
}

@Preview
@Composable
fun CardListGridPreview() {
    val cardsFlow = flowOf(PagingData.from(listOf<CardListing>()))
    val pagingItems = cardsFlow.collectAsLazyPagingItems()
    YGOCollectionTheme {
        CardListGrid(pagingItems)
    }
}