package com.gcirilo.ygocollection.presentation.card_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.gcirilo.ygocollection.domain.model.Archetype
import com.gcirilo.ygocollection.presentation.card_list.CardListEvent
import com.gcirilo.ygocollection.presentation.card_list.CardListState
import com.gcirilo.ygocollection.presentation.card_list.CardQueryItemType
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme

@Composable
fun ArchetypeList(
    state: CardListState = CardListState(),
    filterSurface: Boolean = true,
    onEvent: (CardListEvent, Boolean) -> Unit = { _, _ -> },
) {
    val localFocusManager = LocalFocusManager.current
    AnimatedVisibility(
        visible = filterSurface,
        modifier = Modifier
            .fillMaxSize()
            .zIndex(2f),
        enter = fadeIn(animationSpec = tween(500)),
        exit = fadeOut(animationSpec = tween(500)),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            elevation = 4.dp,
            shape = RoundedCornerShape(2)
        ) {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
            ) {
                item {
                    Text(
                        text = "Archetype",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                items(state.archetypes){ archetype ->
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            localFocusManager.clearFocus()
                            onEvent(
                                CardListEvent.OnCardQueryChange(
                                    CardQueryItemType.ArchetypeFilter(
                                        archetype.name
                                    )
                                ),
                                false
                            )
                        }) {
                        Text(text = archetype.name)
                    }
                }
                item {
                    Divider(Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewArchetypeList(){
    YGOCollectionTheme {
        ArchetypeList(state = CardListState(archetypes = listOf(Archetype("Elemental HERO"))))
    }
}