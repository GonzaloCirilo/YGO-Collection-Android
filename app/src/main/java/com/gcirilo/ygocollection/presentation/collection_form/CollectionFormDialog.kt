package com.gcirilo.ygocollection.presentation.collection_form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gcirilo.ygocollection.domain.model.CollectionForm
import com.gcirilo.ygocollection.presentation.SnackBarCallback
import com.gcirilo.ygocollection.presentation.ui.theme.DarkBlueLight
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CollectionFormDialog(
    navController: NavController,
    snackBarCallback: SnackBarCallback,
) {
    val viewModel: CollectionFormViewModel = hiltViewModel()
    val collectionForm by viewModel.collection.collectAsState()
    CollectionFormDialogContent(navController, collectionForm = collectionForm, onNameChange = { newName ->
        viewModel.onNameChange(newName)
    }, onSaveButton = {
        snackBarCallback("Saved successfully", SnackbarDuration.Short)
        viewModel.saveCollection()
    })
}

@Composable
fun CollectionFormDialogContent(
    navController: NavController,
    collectionForm: CollectionForm,
    onNameChange: (String)->Unit = {},
    onSaveButton: ()->Unit = {},
) {
    val localFocusManager = LocalFocusManager.current
    Box(modifier = Modifier
        .width(280.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(DarkBlueLight),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Collection Name")
            OutlinedTextField(
                value = collectionForm.name,
                placeholder = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                onValueChange = {
                    onNameChange(it)
                },
                maxLines = 1,
                keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            Button(
                modifier = Modifier,
                onClick = {
                    onSaveButton()
                    navController.popBackStack()
                }
            ) {
                Text(text = "Save")
            }
        }
    }
}

@Preview
@Composable
fun CollectionFormDialogPreview(){
    YGOCollectionTheme() {
        CollectionFormDialogContent(rememberNavController(), CollectionForm(0L, ""))
    }
}