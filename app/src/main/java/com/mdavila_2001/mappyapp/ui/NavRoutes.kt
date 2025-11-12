package com.mdavila_2001.mappyapp.ui

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavRoutes(val route: String) {
    object Splash : NavRoutes("splash")
    object login : NavRoutes("login")
    object RoutesForm : NavRoutes("routes_form_screen/{username}?routeId={routeId}") {
        val arguments = listOf(
            navArgument("username") { type = NavType.StringType },
            navArgument("routeId") {
                type = NavType.IntType
                defaultValue = -1
            }
        )

        fun createRoute(username: String): String {
            val encodedUsername = username.replace(" ", "_")
            return "routes_form_screen/$encodedUsername"
        }

        fun createEditRoute(username: String, routeId: Int): String {
            val encodedUsername = username.replace(" ", "_")
            return "routes_form_screen/$encodedUsername?routeId=$routeId"
        }
    }
    object RoutesList : NavRoutes("routes_list_screen/{username}") {
        val arguments = listOf(
            navArgument("username") { type = NavType.StringType }
        )

        fun createRoute(username: String): String {
            val encodedUsername = username.replace(" ", "_")
            return "routes_list_screen/$encodedUsername"
        }
    }
    object MapRoutes : NavRoutes("map_routes_screen/{routeId}/{routeName}") {
        val arguments = listOf(
            navArgument("routeId") { type = NavType.IntType },
            navArgument("routeName") { type = NavType.StringType }
        )

        fun createRoute(routeId: Int, routeName: String): String {
            val encodedName = routeName.replace(" ", "_")
            return "map_routes_screen/$routeId/$encodedName"
        }
    }
    object MapForm : NavRoutes("map_form_screen/{routeId}/{routeName}") {
        val arguments = listOf(
            navArgument("routeId") { type = NavType.IntType },
            navArgument("routeName") { type = NavType.StringType } // También pasamos el nombre para el título
        )

        fun createRoute(routeId: Int, routeName: String): String {
            val encodedName = routeName.replace(" ", "_")
            return "map_form_screen/$routeId/$encodedName"
        }
    }
}