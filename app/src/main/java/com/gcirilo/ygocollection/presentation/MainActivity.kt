package com.gcirilo.ygocollection.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gcirilo.ygocollection.presentation.navigation.BottomNavScreen
import com.gcirilo.ygocollection.presentation.navigation.NavGraphDestinations
import com.gcirilo.ygocollection.presentation.navigation.mainNavGraph
import com.gcirilo.ygocollection.presentation.ui.theme.YGOCollectionTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

typealias SnackBarCallback = (String,SnackbarDuration)->Unit

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YGOCollectionTheme {
                MainYGOComposable()
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MainYGOComposable() {
    val navController = rememberNavController()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomBarNavigation(navController = navController) },
    ) { paddingValues ->
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colors.background
        ) {
            NavHost(navController, startDestination = NavGraphDestinations.Main.route) {
                mainNavGraph(
                    navController,
                    snackBarCallback = { message, duration ->
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(message, duration = duration)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BottomBarNavigation(navController: NavController) {
    val navigationItems = listOf(
        BottomNavScreen.CardListDestination,
        BottomNavScreen.CollectionListDestination,
    )
    BottomNavigation {
        val navStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navStackEntry?.destination?.route
        navigationItems.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title.orEmpty()) },
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(0) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    YGOCollectionTheme {
        Greeting("Android")
    }
}