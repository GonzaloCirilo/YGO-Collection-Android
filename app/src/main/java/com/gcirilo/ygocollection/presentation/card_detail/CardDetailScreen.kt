package com.gcirilo.ygocollection.presentation.card_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme

@Composable
fun CardDetailScreen() {
    val viewModel: CardDetailViewModel = hiltViewModel()
    val id: Long by viewModel.id.collectAsState()
    CardDetailScreenContent(id)
}

@Composable
fun CardDetailScreenContent(id: Long){
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Card Detail $id")
    }
}

@Preview
@Composable
fun CardDetailScreenPreview(){
    YGOCollectionTheme {
        CardDetailScreenContent(0)
    }
}
