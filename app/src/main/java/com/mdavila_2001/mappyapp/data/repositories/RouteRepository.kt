package com.mdavila_2001.mappyapp.data.repositories

import android.util.Log
import com.mdavila_2001.mappyapp.data.remote.models.Route
import com.mdavila_2001.mappyapp.data.remote.models.dto.RouteDTO
import com.mdavila_2001.mappyapp.data.remote.network.ApiService
import com.mdavila_2001.mappyapp.data.remote.network.RetrofitInstance

class RouteRepository {
    private val apiService: ApiService = RetrofitInstance.api

    suspend fun getAllRoutes(): List<Route> {
        try {
            val response = apiService.getAllRoutes()
            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            } else {
                Log.e("RouteRepository", "Error llamando a las rutas: ${response.code()} ${response.message()}")
                return emptyList()
            }
        } catch (e: Exception) {
            Log.e("RouteRepository", "Excepción llamando a las rutas: ${e.message}")
            return emptyList()
        }
    }

    suspend fun getRoutesByUser(username: String): List<Route> {
        try {
            val response = apiService.getRoutesByUser(username)
            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            } else {
                Log.e("RouteRepository", "Error al obtener las rutas del usuario $username: ${response.code()} ${response.message()}")
                return emptyList()
            }
        } catch (e: Exception) {
            Log.e("RouteRepository", "Excepción al obtener rutas $username: ${e.message}")
            return emptyList()
        }
    }

    suspend fun insertRoute(route: RouteDTO): Route? {
        try {
            val response = apiService.insertRoute(route)
            if (response.isSuccessful) {
                return response.body()
            } else {
                Log.e("RouteRepository", "Error insertando la ruta: ${response.code()} ${response.message()}")
                return null
            }
        } catch (e: Exception) {
            Log.e("RouteRepository", "Excepción insertando la ruta: ${e.message}")
            return null
        }
    }

    suspend fun updateRoute(routeId: Int, route: RouteDTO): Route? {
        try {
            val response = apiService.updateRoute(routeId, route)
            if (response.isSuccessful) {
                return response.body()
            } else {
                Log.e("RouteRepository", "Error actualizando la ruta: ${response.code()} ${response.message()}")
                return null
            }
        } catch (e: Exception) {
            Log.e("RouteRepository", "Excepción actualizando la ruta: ${e.message}")
            return null
        }
    }

    suspend fun deleteRoute(routeId: Int): Boolean {
        try {
            val response = apiService.deleteRoute(routeId)
            if (response.isSuccessful) {
                return true
            } else {
                Log.e("RouteRepository", "Error eliminando la ruta: ${response.code()} ${response.message()}")
                return false
            }
        } catch (e: Exception) {
            Log.e("RouteRepository", "Excepción eliminando la ruta: ${e.message}")
            return false
        }
    }
}