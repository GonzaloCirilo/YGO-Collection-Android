package com.gcirilo.ygocollection.presentation.card_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.gcirilo.ygocollection.domain.model.CardListing
import com.gcirilo.ygocollection.presentation.card_list.CardListEvent
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme

@Composable
fun CardListItem(card: CardListing, onEvent: (CardListEvent) -> Unit = {}) {
    Column(modifier = Modifier.aspectRatio(0.69f).fillMaxWidth()) {
        SubcomposeAsyncImage(
            model = card.imageUrl,
            contentDescription = card.name,
            modifier = Modifier.fillMaxWidth().clickable {
                onEvent(CardListEvent.OnCardSelected(card.id))
            }
        ) {
            when (painter.state){
                is AsyncImagePainter.State.Loading, is AsyncImagePainter.State.Error ->
                {
                    CircularProgressIndicator()
                }
                else ->
                    SubcomposeAsyncImageContent(modifier = Modifier.fillMaxWidth())
            }
        }
    }

}

@Preview(widthDp = 220, heightDp = 500)
@Composable
fun PreviewCardListItem(){
    YGOCollectionTheme {
        CardListItem(card = CardListing(10,"Contrast HERO Chaos", "FusionMonster", "https://ygoprodeck.com/pics/23204029.jpg", 3000, 3000, 8, linkval = null, scale = null))
    }
}