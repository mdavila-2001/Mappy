package com.mdavila_2001.mappyapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mdavila_2001.mappyapp.ui.NavRoutes
import com.mdavila_2001.mappyapp.ui.views.LoginScreen
import com.mdavila_2001.mappyapp.ui.views.SplashScreen

@Composable
fun NavigationApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Splash.route,
        modifier = modifier
    ) {
        composable( route = NavRoutes.Splash.route ) {
            SplashScreen(navController)
        }
        composable( route = NavRoutes.Login.route ) {
            LoginScreen(navController)
        }
        composable( route = NavRoutes.RoutesList.route ) {
            PlaceHolderScreen(text = "Lista de Rutas")
        }
        composable(
            route = NavRoutes.RoutesForm.route,
            arguments = NavRoutes.RoutesForm.arguments
        ) { navBackStackEntry ->

            val username = navBackStackEntry.arguments?.getString("username") ?: "Error"
            val routeId = navBackStackEntry.arguments?.getInt("routeId") ?: -1
            PlaceHolderScreen(text = "Formulario de Ruta para $username (ID: $routeId)")
        }

        composable(
            route = NavRoutes.MapRoutes.route,
            arguments = NavRoutes.MapRoutes.arguments
        ) { navBackStackEntry ->
            val routeId = navBackStackEntry.arguments?.getInt("routeId") ?: -1
            val routeName = navBackStackEntry.arguments?.getString("routeName") ?: "Error"
            PlaceHolderScreen(text = "Mapa de Ruta: $routeName (ID: $routeId)")
        }
        composable(
            route = NavRoutes.MapForm.route,
            arguments = NavRoutes.MapForm.arguments
        ) { navBackStackEntry ->

            val routeId = navBackStackEntry.arguments?.getInt("routeId") ?: -1
            val routeName = navBackStackEntry.arguments?.getString("routeName") ?: "Error"

            PlaceHolderScreen(text = "Editando Mapa de: $routeName (ID: $routeId)")
        }
    }
}

@Composable
fun PlaceHolderScreen (
    text: String,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}