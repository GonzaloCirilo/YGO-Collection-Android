package com.gcirilo.ygocollection.presentation.collection_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gcirilo.ygocollection.domain.model.Collection
import com.gcirilo.ygocollection.domain.model.CollectionForm
import com.gcirilo.ygocollection.presentation.collection_list.components.CoilImage
import com.gcirilo.ygocollection.presentation.navigation.Screen
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme

@Composable
fun CollectionListScreen(navController: NavController) {
    val viewModel: CollectionListViewModel = hiltViewModel()
    val collections: List<Collection> by viewModel.collections.collectAsState()
    CollectionListScreenContent(collections, navController)
}

@Composable
fun CollectionListScreenContent(collections: List<Collection>, navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(
            modifier = Modifier,
            onClick = {
                navController.navigate(Screen.CollectionFormDestination.createRoute(mapOf(
                    Screen.CollectionFormDestination.Args.CollectionId to 0L
                )))
            },
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
        }
    }) { padding ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            columns = GridCells.Adaptive(200.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(collections) { collection ->
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
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                Text(text = collection.name, fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun CardArtworkImage(url: String) {
    Column(
        modifier = Modifier
            .aspectRatio(1.0f)
            .fillMaxWidth()
    ) {
        CoilImage(url = url)
    }
}

@Preview
@Composable
fun PreviewContentList(){
    YGOCollectionTheme {
        CollectionListScreenContent(collections = listOf(Collection(0, "HERO Collection",
            emptyList(),0)), rememberNavController())
    }
}