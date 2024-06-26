package com.gcirilo.ygocollection.presentation.collection_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gcirilo.ygocollection.domain.model.Collection
import com.gcirilo.ygocollection.presentation.collection_list.CollectionListScreenEvents.AddButtonClicked
import com.gcirilo.ygocollection.presentation.collection_list.CollectionListScreenEvents.CollectionClicked
import com.gcirilo.ygocollection.presentation.collection_list.components.CoilImage
import com.gcirilo.ygocollection.presentation.navigation.Screen
import com.gcirilo.ygocollection.presentation.navigation.Screen.CollectionDetailDestination.Args.CollectionId
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme

sealed class CollectionListScreenEvents {
    object AddButtonClicked: CollectionListScreenEvents()
    data class CollectionClicked(val collectionId: Long): CollectionListScreenEvents()
}

@Composable
fun CollectionListScreen(navController: NavController) {
    val viewModel: CollectionListViewModel = hiltViewModel()
    val collections: List<Collection> by viewModel.collections.collectAsState()
    CollectionListScreenContent(
        collections = collections,
        onScreenEvent = {
            when (val event = it) {
                AddButtonClicked -> {
                    navController.navigate(
                        Screen.CollectionFormDestination.createRoute(
                            mapOf(
                                Screen.CollectionFormDestination.Args.CollectionId to 0L
                            )
                        )
                    )
                }

                is CollectionClicked -> {
                    navController.navigate(
                        Screen.CollectionDetailDestination.createRoute(
                            mapOf(CollectionId to event.collectionId)
                        )
                    )
                }
            }
        }
    )
}

@Composable
fun CollectionListScreenContent(collections: List<Collection>, onScreenEvent: (CollectionListScreenEvents)->Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AddFloatingActionButton(onClick = { onScreenEvent(AddButtonClicked) })
        }
    ) { padding ->
        if (collections.isNotEmpty()) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(8.dp),
                columns = GridCells.Adaptive(180.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item(
                    span = { GridItemSpan(maxCurrentLineSpan) }
                ) {
                    Text(
                        text = "My Collections",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
                items(collections) { collection ->
                    CollectionCard(
                        collection = collection,
                        onClick = { onScreenEvent(CollectionClicked(collection.id)) },
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No collections", fontSize = 24.sp, fontStyle = FontStyle.Italic)
            }
        }
    }
}

@Composable
fun AddFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier,
        onClick = onClick,
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
    }
}

@Composable
fun CardArtworkImage(url: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .aspectRatio(1.0f)
            .fillMaxWidth()
    ) {
        CoilImage(url = url)
    }
}

@Composable
fun CollectionCard(collection: Collection, onClick: () -> Unit = {}) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        elevation = 8.dp,
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = LocalIndication.current,
            onClick = onClick
        )
    ) {
        Column(Modifier.padding(4.dp)) {
            if (collection.artworkUrls.isNotEmpty()) {
                if (collection.artworkUrls.size == 1) {
                    CardArtworkImage(url = collection.artworkUrls.first())
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .aspectRatio(1.0f)
                            .fillMaxSize(),
                    ) {
                        items(collection.artworkUrls) { artworkUrl ->
                            CardArtworkImage(url = artworkUrl)
                        }
                    }
                }
            } else {
                Image(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "no image",
                    modifier = Modifier
                        .aspectRatio(1.0f)
                        .fillMaxSize()
                        .background(Color.Cyan)
                )
            }
            Text(text = collection.name, fontSize = 24.sp)
        }
    }
}

@Preview
@Composable
fun PreviewContentList() {
    YGOCollectionTheme {
        CollectionListScreenContent(
            collections = listOf(
                Collection(
                    0, "HERO Collection",
                    emptyList(), 0
                )
            ),
            onScreenEvent = { }
        )
    }
}