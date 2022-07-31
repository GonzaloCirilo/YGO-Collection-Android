package com.gcirilo.ygocollection.presentation.card_collection_form

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
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
import com.gcirilo.ygocollection.presentation.ui.theme.DarkBlueLight
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme

@Composable
fun CardCollectionFormDialog(navController: NavController) {
    val viewModel: CardCollectionFormViewModel = hiltViewModel()
    val collections by viewModel.collections.collectAsState()
    CardCollectionFormDialogContent(
        collections = collections,
        navController = navController,
        onCollectionSelected = {
            viewModel.onCollectionSelected(it)
        },
        onSaveCardToCollection = {
            viewModel.onSaveToCollection()
        }
    )
}

@Composable
fun CardCollectionFormDialogContent(
    collections: List<Collection>,
    navController: NavController,
    onCollectionSelected: (Long)->Unit = {},
    onSaveCardToCollection: ()->Unit = {},
) {
    Box(modifier = Modifier
        .width(280.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(DarkBlueLight),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Add to collection")
            CollectionsDropDown(collections = collections) {
                onCollectionSelected(it)
            }
            Button(onClick = {
                onSaveCardToCollection()
                navController.popBackStack()
            }) {
                Text(text = "Add")
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
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    mTextFieldSize = coordinates.size.toSize()
                },
            label = { Text("Select a collection...") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
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

@Preview
@Composable
fun CardCollectionFormDialogPreview() {
    YGOCollectionTheme {
        CardCollectionFormDialogContent(collections = emptyList(), rememberNavController())
    }
}