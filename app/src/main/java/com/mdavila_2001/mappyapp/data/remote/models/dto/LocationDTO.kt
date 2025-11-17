package com.mdavila_2001.mappyapp.data.remote.models.dto

import com.google.gson.annotations.SerializedName

data class LocationDTO (
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("route_id")
    val routeId: Int
)