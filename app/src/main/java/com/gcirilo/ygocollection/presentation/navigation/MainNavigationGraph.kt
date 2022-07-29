package com.gcirilo.ygocollection.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gcirilo.ygocollection.presentation.card_detail.CardDetailScreen
import com.gcirilo.ygocollection.presentation.card_list.CardListScreen

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
    }
}