package com.gcirilo.ygocollection.presentation.card_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.gcirilo.ygocollection.domain.model.Card
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme

@Composable
fun CardDetailScreen() {
    val viewModel: CardDetailViewModel = hiltViewModel()
    val card: Card? by viewModel.card.collectAsState()
    val shouldShowLoadingIndicator by viewModel.shouldShowLoadingIndicator.collectAsState()
    CardDetailScreenContent(card, shouldShowLoadingIndicator)
}

@Composable
fun CardDetailScreenContent(
    card: Card?,
    shouldShowLoadingIndicator: Boolean,
){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
    ) {
        if(card != null) {
            Column(modifier = Modifier
                .aspectRatio(0.69f)
                .fillMaxWidth()
            ) {
                SubcomposeAsyncImage(
                    model = card.imageUrl,
                    contentDescription = card.name,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    when (painter.state){
                        is AsyncImagePainter.State.Loading,
                        is AsyncImagePainter.State.Error -> { CircularProgressIndicator() }
                        else -> SubcomposeAsyncImageContent(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
            Text(text = card.name)
            Text(text = card.desc, textAlign = TextAlign.Justify)
            Text(text = "Prices")
            Text(text = "Ebay: ${card.avgEbayPrice}")
            Text(text = "Amazon: ${card.avgAmazonPrice}")
            Text(text = "TCGPlayer: ${card.avgTcgplayerPrice}")
        }else if(shouldShowLoadingIndicator) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun CardDetailScreenPreview(){
    YGOCollectionTheme {
        CardDetailScreenContent(Card(
            id = 0L,
            name = "HERO",
            type = "Fusion",
            desc = "description",
            archetype = "archetype",
            atk = 2000,
            def = 2000,
            level = 4,
            attribute = "WIND",
            scale = null,
            linkval = null,
            avgAmazonPrice = 3.40,
            avgEbayPrice = 4.20,
            avgTcgplayerPrice = 5.60,
            imageUrl = "",
            linkMarkers = null,
            race = "Effect Monster"

        ), false)
    }
}