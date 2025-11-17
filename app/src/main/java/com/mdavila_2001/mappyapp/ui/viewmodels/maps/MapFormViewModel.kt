package com.mdavila_2001.mappyapp.ui.viewmodels.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.mdavila_2001.mappyapp.data.remote.models.Location
import com.mdavila_2001.mappyapp.data.remote.models.dto.LocationDTO
import com.mdavila_2001.mappyapp.data.repositories.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MapFormUIState(
    val isLoading: Boolean = true,
    val locations: List<Location> = emptyList()
)

class MapFormViewModel : ViewModel() {
    private val repository = LocationRepository()

    private val _uiState = MutableStateFlow(MapFormUIState())
    val uiState: StateFlow<MapFormUIState> = _uiState.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun clearToastMessage() {
        _toastMessage.value = null
    }

    fun loadLocations(routeId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val locationsList = repository.getLocationsByRoute(routeId)

            _uiState.update {
                it.copy(isLoading = false, locations = locationsList)
            }
        }
    }

    fun addLocation(point: LatLng, routeId: Int) {
        viewModelScope.launch {
            val newLocationDTO = LocationDTO(
                latitude = point.latitude.toString(),
                longitude = point.longitude.toString(),
                routeId = routeId
            )

            val newLocationFromAPI = repository.insertLocation(newLocationDTO)

            if (newLocationFromAPI != null) {
                _uiState.update { currentState ->
                    currentState.copy(
                        locations = currentState.locations + newLocationFromAPI
                    )
                }
                loadLocations(routeId = routeId)
                _toastMessage.value = "Punto guardado correctamente"
            } else {
                _toastMessage.value = "Error al guardar el punto"
            }
        }
    }

    fun deleteLocation(location: Location) {
        viewModelScope.launch {
            val isDeleted = repository.deleteLocation(location.id)

            if (isDeleted) {
                _uiState.update { currentState ->
                    currentState.copy(
                        locations = currentState.locations - location
                    )
                }
                loadLocations(routeId = location.routeId)
                _toastMessage.value = "Punto eliminado correctamente"
            } else {
                _toastMessage.value = "Error al eliminar el punto"
            }
        }
    }

    fun updateLocation(location: Location, newPosition: LatLng) {
        viewModelScope.launch {
            val updatedLocationDTO = LocationDTO(
                latitude = newPosition.latitude.toString(),
                longitude = newPosition.longitude.toString(),
                routeId = location.routeId
            )

            val updatedLocation = repository.updateLocation(location.id, updatedLocationDTO)

            if (updatedLocation != null) {
                _uiState.update { currentState ->
                    currentState.copy(
                        locations = currentState.locations.map {
                            if (it.id == location.id) updatedLocation else it
                        }
                    )
                }
                _toastMessage.value = "Ubicación actualizada"
            } else {
                _toastMessage.value = "Error al actualizar la ubicación"
            }
        }
    }
}