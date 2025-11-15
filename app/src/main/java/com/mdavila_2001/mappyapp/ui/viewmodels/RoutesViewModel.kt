package com.mdavila_2001.mappyapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mdavila_2001.mappyapp.data.remote.models.Route
import com.mdavila_2001.mappyapp.data.repositories.RouteRepository
import com.mdavila_2001.mappyapp.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoutesViewModel(application: Application): AndroidViewModel(application) {
    private val repository = RouteRepository()
    private val sessionManager = SessionManager(application)

    private val _list = MutableStateFlow<List<Route>>(emptyList())
    val list: StateFlow<List<Route>> = _list.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _currentUsername = MutableStateFlow("")
    val currentUsername: StateFlow<String> = _currentUsername.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun clearToastMessage() {
        _toastMessage.value = null
    }

    fun loadRoutes(userName: String?) {
        viewModelScope.launch {
            _isLoading.value = true

            if(userName == null) {
                _list.value = repository.getAllRoutes()
                _currentUsername.value = sessionManager.getUserName()  ?: ""
            } else {
                _list.value = repository.getRoutesByUser(userName)
                _currentUsername.value = userName
            }
            _isLoading.value = false
        }
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    fun deleteRoute(route: Route) {
        viewModelScope.launch {
            val success = repository.deleteRoute(route.id)
            if(success) {
                _list.update { currentList ->
                    currentList.filterNot { it.id == route.id }
                }
            } else {
                _toastMessage.value = "Error al eliminar la ruta"
            }
        }
    }
}