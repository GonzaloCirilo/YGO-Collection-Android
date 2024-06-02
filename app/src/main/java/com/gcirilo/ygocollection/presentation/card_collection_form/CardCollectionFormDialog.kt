package com.gcirilo.ygocollection.presentation.card_collection_form

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gcirilo.ygocollection.domain.model.Collection
import com.gcirilo.ygocollection.presentation.SnackBarCallback
import com.gcirilo.ygocollection.presentation.ui.theme.DarkBlueLight
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme

@Composable
fun CardCollectionFormDialog(
    navController: NavController,
    snackBarCallback: SnackBarCallback,
) {
    val viewModel: CardCollectionFormViewModel = hiltViewModel()
    val collections by viewModel.collections.collectAsState()
    val cardCount by viewModel.cardCount.collectAsState()
    val hasSelected by viewModel.hasSelected.collectAsState()

    CardCollectionFormDialogContent(
        collections = collections,
        navController = navController,
        cardCount = cardCount,
        hasSelected = hasSelected,
        onCollectionSelected = {
            viewModel.onCollectionSelected(it)
        },
        onSaveCardToCollection = {
            snackBarCallback("Added To Collection", SnackbarDuration.Short)
            viewModel.onSaveToCollection()
        },
        onUpdateCount = {
            viewModel.onCardCountUpdate(it)
        }
    )
}

@Composable
fun CardCollectionFormDialogContent(
    collections: List<Collection>,
    cardCount: Int = 0,
    hasSelected: Boolean = false,
    navController: NavController,
    onCollectionSelected: (Long)->Unit = {},
    onSaveCardToCollection: ()->Unit = {},
    onUpdateCount: (Int)->Unit = {},
) {
    Box(modifier = Modifier
        .width(280.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(DarkBlueLight),
    ) {
        Column(modifier = Modifier
            .padding(16.dp)) {
            Text(text = "Add to collection")
            if (collections.isNotEmpty()) {
                CollectionsDropDown(
                    collections = collections,
                    onSelected = {
                        onCollectionSelected(it)
                    },
                )
                Text(text = "Quantity")
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    IconButton(onClick = { onUpdateCount(-1) }, enabled = hasSelected) {
                        Icon(Icons.Filled.Delete, "remove")
                    }
                    Text(text = cardCount.toString())
                    IconButton(onClick = { onUpdateCount(1) }, enabled = hasSelected) {
                        Icon(Icons.Filled.Add, "remove")
                    }
                }
                Button(
                    onClick = {
                        onSaveCardToCollection()
                        navController.popBackStack()
                    },
                    enabled = hasSelected
                ) {
                    Text(text = "Add")
                }
            } else {
                NoCollectionMessage()
            }
        }
    }
}

@Composable
fun CollectionsDropDown(collections: List<Collection>, onSelected: (Long)->Unit = {}) {

    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }

    // Create a string value to store the selected city
    var mSelectedText by remember { mutableStateOf("") }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(vertical = 20.dp)) {

        // Create an Outlined Text Field
        // with icon and not expanded
        OutlinedTextField(
            value = mSelectedText,
            onValueChange = { mSelectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    mExpanded = !mExpanded
                }
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    mTextFieldSize = coordinates.size.toSize()
                },
            label = { Text("Select a collection...") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            },
            enabled = false
        )

        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
        ) {
            collections.forEach { collection ->
                DropdownMenuItem(onClick = {
                    mSelectedText = collection.name
                    mExpanded = false
                    onSelected(collection.id)
                }) {
                    Text(text = collection.name)
                }
            }
        }
    }
}

@Composable
private fun NoCollectionMessage() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "No collections found")
    }
}

@Preview
@Composable
fun CardCollectionFormDialogPreview() {
    YGOCollectionTheme {
        CardCollectionFormDialogContent(
            collections = listOf(Collection(1, "Collection 1", emptyList(), 1)),
            0,
            false,
            rememberNavController()
        )
    }
}
@Preview
@Composable
fun CardCollectionFormDialogEmptyPreview() {
    YGOCollectionTheme {
        CardCollectionFormDialogContent(
            collections = emptyList(),
            0,
            false,
            rememberNavController()
        )
    }
}