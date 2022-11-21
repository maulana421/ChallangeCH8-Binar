package com.solanacode.challange8.model


import com.google.gson.annotations.SerializedName

data class UserResponseItem(
    @SerializedName("username")
    val username: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("id")
    val id: String



)