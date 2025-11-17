package com.mdavila_2001.mappyapp.ui.views

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mdavila_2001.mappyapp.ui.NavRoutes
import com.mdavila_2001.mappyapp.ui.components.global.AppBar
import com.mdavila_2001.mappyapp.ui.components.global.BottomNavigationBar
import com.mdavila_2001.mappyapp.ui.components.routes.RouteList
import com.mdavila_2001.mappyapp.ui.viewmodels.routes.RoutesViewModel
import com.mdavila_2001.mappyapp.ui.viewmodels.routes.Tab

@Composable
fun RoutesScreen(
    navController: NavController,
    viewModel: RoutesViewModel = viewModel(),
    userName: String?
) {
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()
    val filteredRoutes by viewModel.filteredRoutes.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()
    val navigateToLogin by viewModel.navigateToLogin.collectAsState()

    val showLogoutDialogState = remember { mutableStateOf(false) }

    LaunchedEffect(toastMessage) {
        if (toastMessage != null) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearToastMessage()
        }
    }

    LaunchedEffect(userName) {
        if (userName == null) {
            viewModel.onTabSelected(Tab.ROUTES)
        } else {
            viewModel.onTabSelected(Tab.MY_ROUTES)
        }
    }

    LaunchedEffect(navigateToLogin) {
        if (navigateToLogin) {
            navController.navigate(NavRoutes.Login.route) {
                popUpTo(0)
            }
            viewModel.onNavigationDone()
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                title = if(uiState.selectedTab == Tab.MY_ROUTES) "Mis Rutas" else "Rutas",
                backEnabled = false,
                logOutEnabled = true,
                onLogoutClick = {
                    showLogoutDialogState.value = true
                },
                onBackClick = {},
                modifier = Modifier
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = if (uiState.selectedTab == Tab.ROUTES) 0 else 1,
                onTabSelected = { index ->
                    val newTab = if (index == 0) Tab.ROUTES else Tab.MY_ROUTES
                    viewModel.onTabSelected(newTab)
                },
                modifier = Modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(NavRoutes.RoutesForm.createRoute(uiState.currentUsername))
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    "Crear Ruta"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                RouteList(
                    routes = filteredRoutes,
                    isMine = (uiState.selectedTab == Tab.MY_ROUTES),
                    onRouteClick = { route ->
                        if (uiState.selectedTab == Tab.MY_ROUTES) {
                            navController.navigate(
                                NavRoutes.MapForm.createRoute(route.id, route.name)
                            )
                        } else {
                            navController.navigate(
                                NavRoutes.MapRoutes.createRoute(route.id, route.name)
                            )
                        }
                    },
                    onEditClick = { route ->
                        navController.navigate(
                            NavRoutes.RoutesForm.createEditRoute(uiState.currentUsername, route.id, route.name)
                        )
                    },
                    onDeleteClick = { route ->
                        viewModel.deleteRoute(route)
                    }
                )
            }
        }
    }

    if (showLogoutDialogState.value) {
        AlertDialog(
            onDismissRequest = { showLogoutDialogState.value = false },
            title = { Text("Confirmar") },
            text = { Text("¿Seguro que deseas cerrar sesión?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialogState.value = false
                    viewModel.onLogoutClicked()
                }) {
                    Text("Cerrar sesión")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialogState.value = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}