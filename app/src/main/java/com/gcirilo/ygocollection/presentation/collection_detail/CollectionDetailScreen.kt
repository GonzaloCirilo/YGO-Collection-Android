package com.gcirilo.ygocollection.presentation.collection_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gcirilo.ygocollection.domain.model.CardListing
import com.gcirilo.ygocollection.domain.model.CollectionCards
import com.gcirilo.ygocollection.presentation.card_list.components.CardListGrid
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme

@Composable
fun CollectionDetailScreen() {
    val viewModel: CollectionDetailViewModel = hiltViewModel()
    val cardCollection: CollectionCards? by viewModel.collection.collectAsState()
    CollectionDetailScreenContent(
        collectionCards = cardCollection,
        onCardSelected = { viewModel.onDeleteCard(it) }
    )
}

@Composable
fun CollectionDetailScreenContent(
    collectionCards: CollectionCards?,
    onCardSelected: (Long)->Unit = {},
) {
    if (collectionCards != null) {
        Column {
            Text(text = collectionCards.name, fontSize = 36.sp)
            if (collectionCards.cards.isNotEmpty()) {
                CardListGrid(
                    cards = collectionCards.cards.map {
                        CardListing(
                            id = it.id,
                            name = it.name,
                            type = it.type,
                            imageUrl = it.imageUrl,
                            atk = it.atk,
                            def = it.def,
                            level = it.level,
                            scale = it.scale,
                            linkval = it.linkval
                        )
                    },
                    onCardSelected = onCardSelected
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewCardListScreenContent() {
    YGOCollectionTheme {
        CollectionDetailScreenContent(CollectionCards(0, "My Collection", emptyList()))
    }
}