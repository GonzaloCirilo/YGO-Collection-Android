package com.gcirilo.ygocollection.presentation.navigation

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(route: String, val title: String, private val args: List<Arguments> = emptyList()) {

    /**
     * Creates a route of an instance of an Screen object according to
     * the defined arguments
     */
    val route by lazy {
        val arguments: List<Arguments> = args.filter { !it.isOptional }
        val optionalArgs: List<Arguments> = args.filter { it.isOptional }
        StringBuilder(route).apply {
            append(
                arguments.joinToString {
                    "/{${it.key}}"
                }
            )
            append(
                optionalArgs.joinToString(separator = "&", prefix = "?") {
                    "${it.key}={${it.key}}"
                }
            )
        }.toString()
    }

    /**
     * Creates a route with the given map as values as arguments
     */
    fun <T : Arguments> createRoute(args: Map<T, Any>? = null): String {
        var address: String = route

        this.args.forEach {
            val value = args?.get(it)
            address = address.replace("{${it.key}}", value.toString())
        }
        Log.d(Screen::class.java.simpleName, address)
        return address
    }

    /**
     * Returns array of NamedNavArguments defined in Screen Object
     */
    val arguments by lazy {
        args.map {
            navArgument(it.key) {
                type = it.type
            }
        }.toList()
    }

    // MARK: Screens

    object CardDetailDestination : Screen(
        "cardDetail",
        "",
        Args.values().toList()
    ) {
        enum class Args(
            override var isOptional: Boolean = false,
            override var key: String,
            override var type: NavType<*> = NavType.StringType
        ) : Arguments {
            CardId(key = "cardId"),
        }
    }

    interface Arguments {
        val isOptional: Boolean
        val key: String
        val type: NavType<*>
    }
}

sealed class BottomNavScreen(withRoute: String, val icon: ImageVector, title: String):
    Screen(withRoute, title) {
        object CardListDestination: BottomNavScreen("cardList", Icons.Filled.Menu, "Cards")
        object Profile: BottomNavScreen("profile", Icons.Filled.Menu, "Perfil")
}

sealed class NavGraphDestinations(val route: String) {
    object Main: NavGraphDestinations("mainGraph")

}
