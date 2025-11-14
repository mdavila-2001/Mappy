package com.mdavila_2001.mappyapp.data.remote.network

import com.mdavila_2001.mappyapp.data.remote.models.Location
import com.mdavila_2001.mappyapp.data.remote.models.Route
import com.mdavila_2001.mappyapp.data.remote.models.dto.LocationDTO
import com.mdavila_2001.mappyapp.data.remote.models.dto.RouteDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("routes")
    suspend fun getRoutes(): Response<List<Route>>

    @GET("routes/{username}")
    suspend fun getRoutesByUser(
        @Path("username") username: String
    ): Response<List<Route>>

    @POST("routes")
    suspend fun insertRoute(
        @Body route: RouteDTO
    ): Response<Route>

    @PUT("routes/{id}")
    suspend fun updateRoute(
        @Path("id") id: Int,
        @Body route: RouteDTO
    ): Response<Route>

    @DELETE("routes/{id}")
    suspend fun deleteRoute(
        @Path("id") id: Int
    ): Response<Unit>

    @GET("locations")
    suspend fun getLocations(): Response<List<Location>>

    @GET("routes/{route_id}/locations")
    suspend fun getLocationsByRoute(
        @Path("route_id") routeId: Int
    ): Response<List<Location>>

    @POST("locations")
    suspend fun insertLocation(
        @Body location: LocationDTO
    ): Response<Location>

    @PUT("locations/{id}")
    suspend fun updateLocation(
        @Path("id") id: Int,
        @Body location: LocationDTO
    ): Response<Location>

    @DELETE("locations/{id}")
    suspend fun deleteLocation(
        @Path("id") id: Int
    ): Response<Unit>
}