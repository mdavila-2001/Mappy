package com.mdavila_2001.mappyapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class Location (
    @SerializedName("id")
    val id: Int,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("route_id")
    val routeId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)