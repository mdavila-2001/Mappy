package com.mdavila_2001.mappyapp.data.remote.models.dto

import com.google.gson.annotations.SerializedName

data class LocationDTO (
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("route_id")
    val routeId: Int
)