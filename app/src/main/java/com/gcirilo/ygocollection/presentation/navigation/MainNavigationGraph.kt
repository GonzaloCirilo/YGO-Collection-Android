package com.gcirilo.ygocollection.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.gcirilo.ygocollection.presentation.SnackBarCallback
import com.gcirilo.ygocollection.presentation.card_collection_form.CardCollectionFormDialog
import com.gcirilo.ygocollection.presentation.card_detail.CardDetailScreen
import com.gcirilo.ygocollection.presentation.card_list.CardListScreen
import com.gcirilo.ygocollection.presentation.collection_detail.CollectionDetailScreen
import com.gcirilo.ygocollection.presentation.collection_form.CollectionFormDialog
import com.gcirilo.ygocollection.presentation.collection_list.CollectionListScreen

@ExperimentalMaterialApi
fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    snackBarCallback: SnackBarCallback,
){
    navigation(
        startDestination = BottomNavScreen.CardListDestination.route,
        route = NavGraphDestinations.Main.route
    ){
        composable(
            route = BottomNavScreen.CardListDestination.route
        ){
            CardListScreen(navController)
        }

        composable(
            route = Screen.CardDetailDestination.route,
            arguments = Screen.CardDetailDestination.arguments,
        ){
            CardDetailScreen(navController)
        }

        composable(
            route = BottomNavScreen.CollectionListDestination.route,
        ){
            CollectionListScreen(navController)
        }

        composable(
            route = Screen.CollectionDetailDestination.route,
            arguments = Screen.CollectionDetailDestination.arguments,
        ) {
            CollectionDetailScreen()
        }

        dialog(
            route = Screen.CollectionFormDestination.route,
            arguments = Screen.CollectionFormDestination.arguments,
            dialogProperties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            )
        ) {
            CollectionFormDialog(navController, snackBarCallback)
        }

        dialog(
            route = Screen.CardCollectionFormDestination.route,
            arguments = Screen.CardCollectionFormDestination.arguments,
            dialogProperties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            )
        ) {
            CardCollectionFormDialog(navController, snackBarCallback)
        }

    }
}