package com.mdavila_2001.mappyapp.data.remote.models.dto

import com.google.gson.annotations.SerializedName

data class RouteDTO (
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val username: String
)