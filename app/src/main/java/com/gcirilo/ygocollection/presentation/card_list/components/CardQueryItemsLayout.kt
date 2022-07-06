package com.gcirilo.ygocollection.presentation.card_list.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gcirilo.ygocollection.domain.model.CardQuery
import com.gcirilo.ygocollection.presentation.card_list.CardListEvent
import com.gcirilo.ygocollection.presentation.card_list.CardListState
import com.gcirilo.ygocollection.presentation.card_list.CardQueryItemType
import com.gcirilo.ygocollection.presentation.card_list.toCardQueryItemTypes
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme

@ExperimentalMaterialApi
@Composable
fun CardQueryItemsLayout(
    state: CardListState = CardListState(),
    onEvent: (CardListEvent) -> Unit = {},
) {
    val cardQueryItemTypes = state.query.toCardQueryItemTypes()

    AnimatedVisibility(
        visible = cardQueryItemTypes.isNotEmpty(),
        enter = fadeIn(animationSpec = tween(500)),
        exit = fadeOut(animationSpec = tween(500)),
    ) {
        LazyRow(modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()) {
            items(cardQueryItemTypes){ cardQueryItem ->
                Chip(onClick = {
                    when(cardQueryItem){
                        is CardQueryItemType.ArchetypeFilter -> {
                            onEvent(CardListEvent.OnCardQueryChange(CardQueryItemType.ArchetypeFilter(null)))
                        }
                        is CardQueryItemType.RaceFilter -> {
                            onEvent(CardListEvent.OnCardQueryChange(
                                CardQueryItemType.RaceFilter(
                                    cardQueryItem.stringValue.orEmpty(),
                                    cardQueryItem.label.orEmpty(),
                                    true,
                                )
                            ))
                        }
                        else -> {}
                    }
                }) {
                    when(cardQueryItem){
                        is CardQueryItemType.ArchetypeFilter -> {
                            Text(text = cardQueryItem.stringValue.orEmpty())
                        }
                        else -> {
                            Text(text = cardQueryItem.label.orEmpty())
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun CardQueryItemsLayoutPreview() {
    YGOCollectionTheme {
        CardQueryItemsLayout(state = CardListState(query = CardQuery()))
    }
}