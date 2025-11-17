package com.mdavila_2001.mappyapp.ui.viewmodels.routes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdavila_2001.mappyapp.data.remote.models.dto.RouteDTO
import com.mdavila_2001.mappyapp.data.repositories.RouteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RouteFormUIState (
    val routeName: String = "",
    val isLoading: Boolean = false,
    val navigateBack: Boolean = false
)

class RoutesFormViewModel: ViewModel() {
    private val repository = RouteRepository()

    private val _uiState = MutableStateFlow(RouteFormUIState())
    val uiState: StateFlow<RouteFormUIState> = _uiState.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun clearToastMessage() {
        _toastMessage.value = null
    }

    fun loadRoute(name: String) {
        _uiState.update { it.copy(routeName = name) }
    }

    fun onNameChanged(newName: String) {
        _uiState.update { it.copy(routeName = newName) }
    }

    fun onSaveClicked(userName: String, routeId: Int) {
        if (_uiState.value.routeName.isBlank()) {
            _toastMessage.value = "Name cannot be blank"
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val routeDTO = RouteDTO(
                name = _uiState.value.routeName.trim(),
                username = userName
            )

            val success: Boolean

            if (routeId == -1) {
                success = repository.insertRoute(routeDTO) != null
            } else {
                success = repository.updateRoute(routeId, routeDTO) != null
            }

            if (success) {
                _toastMessage.value = if (routeId == -1) "Ruta creada con éxito" else "Ruta actualizada correctamente"
                _uiState.update { it.copy(navigateBack = true) }
            } else {
                _toastMessage.value = "Error al crear la ruta"
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}