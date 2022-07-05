package com.gcirilo.ygocollection.presentation.card_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
fun CardListGrid(cards: LazyPagingItems<CardListing>){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(cards){ card ->
            card?.let {
                CardListItem(card = card)
            }
        }
    }
}

inline fun <T : Any> LazyGridScope.items(
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