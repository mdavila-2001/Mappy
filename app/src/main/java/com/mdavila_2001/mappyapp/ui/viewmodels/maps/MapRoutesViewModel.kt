package com.mdavila_2001.mappyapp.ui.viewmodels.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdavila_2001.mappyapp.data.remote.models.Location
import com.mdavila_2001.mappyapp.data.repositories.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MapRoutesUIState(
    val isLoading: Boolean = false,
    val locations: List<Location> = emptyList()
)

class MapRoutesViewModel: ViewModel() {
    private val repository = LocationRepository()

    private val _uiState = MutableStateFlow(MapRoutesUIState())
    val uiState: StateFlow<MapRoutesUIState> = _uiState.asStateFlow()

    fun loadLocations(routeId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val locationsList = repository.getLocationsByRoute(routeId)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    locations = locationsList
                )
            }
        }
    }
}