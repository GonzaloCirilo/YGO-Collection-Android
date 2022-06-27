package com.gcirilo.ygocollection.presentation.card_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.gcirilo.ygocollection.data.local.CardQuery
import com.gcirilo.ygocollection.domain.model.CardListing
import com.gcirilo.ygocollection.presentation.card_list.components.CardListItem
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme


@Composable
fun CardListScreen(){
    val viewModel: CardListViewModel = hiltViewModel()
    val cards = viewModel.getPagedCards(query = CardQuery()).collectAsLazyPagingItems()
    CardListScreenContent(cards = cards)
}

@Composable
fun CardListScreenContent(cards: LazyPagingItems<CardListing>){
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

@Preview(showBackground = true, widthDp = 220, heightDp = 500)
@Composable
fun PreviewCardListScreenContent() {
    YGOCollectionTheme {
        //CardListScreenContent()
    }
}