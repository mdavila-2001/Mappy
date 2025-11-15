package com.mdavila_2001.mappyapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class Route(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
)