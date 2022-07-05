package com.gcirilo.ygocollection.presentation.card_list.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gcirilo.ygocollection.R
import com.gcirilo.ygocollection.domain.model.CardQuery
import com.gcirilo.ygocollection.presentation.card_list.CardListEvent
import com.gcirilo.ygocollection.presentation.card_list.CardQueryItemType
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme

@Composable
fun CardSearchBar(
    filterSearch: String = "",
    cardQuery: CardQuery = CardQuery(),
    onFilterFocusChange: (isFocused: Boolean)->Unit = {},
    onEvent: (CardListEvent)-> Unit = {}
) {
    val localFocusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 2.dp)
    ) {
        OutlinedTextField(
            value = cardQuery.cardName.orEmpty(),
            placeholder = { Text("Search") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "search") },
            trailingIcon = {
                ClearTextTrailingIcon(text = cardQuery.cardName.orEmpty(), onClick = {
                    onEvent(CardListEvent.OnCardQueryChange(CardQueryItemType.NameSearch("")))
                })
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            onValueChange = {
                onEvent(CardListEvent.OnCardQueryChange(CardQueryItemType.NameSearch(it)))
            },
            maxLines = 1,
            keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        OutlinedTextField(
            value = filterSearch,
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .onFocusChanged { focusState ->
                    Log.d("CardScreenBar", focusState.isFocused.toString())
                    onFilterFocusChange(focusState.isFocused)
                },
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_baseline_filter_list_24),
                    contentDescription = "filter"
                )
            },
            trailingIcon = {
                ClearTextTrailingIcon(text = filterSearch, onClick = {
                    onEvent(CardListEvent.OnArchetypeChange(""))
                })
            },
            onValueChange = {
                onEvent(CardListEvent.OnArchetypeChange(it))
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() }),
        )
    }
}

@Composable
fun ClearTextTrailingIcon(text: String = "", onClick: ()->Unit) {
    val localFocusManager = LocalFocusManager.current
    AnimatedVisibility(
        visible = text.isNotEmpty(),
        enter = fadeIn(animationSpec = tween(500)),
        exit = fadeOut(animationSpec = tween(500)),
    ) {
        Icon(
            Icons.Default.Close,
            contentDescription = "Close",
            modifier = Modifier.clickable {
                localFocusManager.clearFocus()
                onClick()
            }
        )
    }
}



@Preview
@Composable
fun PreviewCardSearchBar(){
    YGOCollectionTheme {
        CardSearchBar()
    }
}