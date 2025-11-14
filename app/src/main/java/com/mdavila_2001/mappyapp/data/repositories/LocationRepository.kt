package com.mdavila_2001.mappyapp.data.repositories

import android.util.Log
import com.mdavila_2001.mappyapp.data.remote.models.Location
import com.mdavila_2001.mappyapp.data.remote.models.dto.LocationDTO
import com.mdavila_2001.mappyapp.data.remote.network.ApiService
import com.mdavila_2001.mappyapp.data.remote.network.RetrofitInstance

class LocationRepository {
    private val api: ApiService = RetrofitInstance.api

    suspend fun getLocationsByRoute(routeId: Int): List<Location> {
        val response = api.getLocationsByRoute(routeId)
        try {
            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            } else {
                Log.e("LocationRepository", "Error al obtener los puntos para la ruta $routeId: ${response.code()} ${response.message()}")
                return emptyList()
            }
        } catch (e: Exception) {
            Log.e("LocationRepository", "Excepción al obtener los puntos: ${e.message}")
            return emptyList()
        }
    }

    suspend fun insertLocation(location: LocationDTO): Location? {
        val response = api.insertLocation(location)
        try {
            if (response.isSuccessful) {
                return response.body()
            } else {
                Log.e("LocationRepository", "Error insertando el punto: ${response.code()} ${response.message()}")
                return null
            }
        } catch (e: Exception) {
            Log.e("LocationRepository", "Excepción insertando el punto: ${e.message}")
            return null
        }
    }

    suspend fun updateLocation(locationId: Int, location: LocationDTO): Location? {
        val response = api.updateLocation(locationId, location)
        try {
            if (response.isSuccessful) {
                return response.body()
            } else {
                Log.e("LocationRepository", "Error actualizando el punto: ${response.code()} ${response.message()}")
                return null
            }
        } catch (e: Exception) {
            Log.e("LocationRepository", "Excepción actualizando el punto: ${e.message}")
            return null
        }
    }

    suspend fun deleteLocation(locationId: Int): Boolean {
        val response = api.deleteLocation(locationId)
        try {
            if (response.isSuccessful) {
                return true
            } else {
                Log.e("LocationRepository", "Error eliminando el punto: ${response.code()} ${response.message()}")
                return false
            }
        } catch (e: Exception) {
            Log.e("LocationRepository", "Excepción eliminando el punto: ${e.message}")
            return false
        }
    }
}