package com.gcirilo.ygocollection.presentation.card_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme

@Composable
fun CardDetailScreen() {
    CardDetailScreenContent()
}

@Composable
fun CardDetailScreenContent(){
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Card Detail")
    }
}

@Preview
@Composable
fun CardDetailScreenPreview(){
    YGOCollectionTheme {
        CardDetailScreenContent()
    }
}
