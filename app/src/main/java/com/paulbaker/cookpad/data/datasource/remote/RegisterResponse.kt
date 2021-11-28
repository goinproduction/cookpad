package com.paulbaker.cookpad.data.datasource.remote


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("token")
    val token: String?
)