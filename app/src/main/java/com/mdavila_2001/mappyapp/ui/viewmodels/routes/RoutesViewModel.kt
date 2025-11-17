package com.mdavila_2001.mappyapp.ui.viewmodels.routes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mdavila_2001.mappyapp.data.remote.models.Route
import com.mdavila_2001.mappyapp.data.repositories.RouteRepository
import com.mdavila_2001.mappyapp.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class Tab {
    ROUTES,
    MY_ROUTES
}

data class RoutesUIState(
    val isLoading: Boolean = true,
    val selectedTab: Tab = Tab.ROUTES,
    val list: List<Route> = emptyList(),
    val searchText: String = "",
    val currentUsername: String = "",
)

class RoutesViewModel(application: Application): AndroidViewModel(application) {
    private val repository = RouteRepository()
    private val sessionManager = SessionManager(application)

    private val _uiState = MutableStateFlow(RoutesUIState())
    val uiState: StateFlow<RoutesUIState> = _uiState.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    private val _navigateToLogin = MutableStateFlow(false)
    val navigateToLogin: StateFlow<Boolean> = _navigateToLogin.asStateFlow()

    val filteredRoutes: StateFlow<List<Route>> = _uiState
        .map { state ->
            if (state.searchText.isBlank()) {
                state.list
            } else {
                state.list.filter { route ->
                    route.name.contains(state.searchText, ignoreCase = true) ||
                            route.username.contains(state.searchText, ignoreCase = true)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        val loggedInUser = sessionManager.getUserName() ?: ""
        _uiState.update { it.copy(currentUsername = loggedInUser) }

        loadRoutes()
    }

    private fun loadRoutes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val tab = _uiState.value.selectedTab
            val routes = if (tab == Tab.ROUTES) {
                repository.getAllRoutes()
            } else {
                repository.getRoutesByUser(uiState.value.currentUsername)
            }
            _uiState.update { it.copy(list = routes, isLoading = false) }
        }
    }

    fun onTabSelected(tab: Tab) {
        _uiState.update { it.copy(selectedTab = tab) }
        loadRoutes()
    }

    fun onSearchTextChanged(text: String) {
        _uiState.update { it.copy(searchText = text) }
    }

    fun deleteRoute(route: Route) {
        viewModelScope.launch {
            val success = repository.deleteRoute(route.id)
            if(success) {
                _uiState.update { currentState ->
                    currentState.copy(
                        list = currentState.list.filterNot { it.id == route.id }
                    )
                }
                loadRoutes()
                _toastMessage.value = "Ruta eliminada correctamente"
            } else {
                _toastMessage.value = "Error al eliminar la ruta"
            }
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }

    fun onNavigationDone() {
        _navigateToLogin.value = false
    }

    fun onLogoutClicked() {
        sessionManager.logout()
        _navigateToLogin.value = true
    }
}