package com.gcirilo.ygocollection.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.gcirilo.ygocollection.presentation.card_detail.CardDetailScreen
import com.gcirilo.ygocollection.presentation.card_list.CardListScreen
import com.gcirilo.ygocollection.presentation.collection_form.CollectionFormDialog
import com.gcirilo.ygocollection.presentation.collection_list.CollectionListScreen

@ExperimentalMaterialApi
fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
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
            CardDetailScreen()
        }

        composable(
            route = BottomNavScreen.CollectionListDestination.route,
        ){
            CollectionListScreen(navController)
        }

        dialog(
            route = Screen.CollectionFormDestination.route,
            arguments = Screen.CollectionFormDestination.arguments,
            dialogProperties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            )
        ) {
            CollectionFormDialog(navController)
        }

    }
}