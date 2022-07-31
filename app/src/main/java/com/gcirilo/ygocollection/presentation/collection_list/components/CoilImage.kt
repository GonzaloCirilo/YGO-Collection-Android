package com.gcirilo.ygocollection.presentation.collection_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun CoilImage(url: String) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = url,
        modifier = Modifier.fillMaxWidth()
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