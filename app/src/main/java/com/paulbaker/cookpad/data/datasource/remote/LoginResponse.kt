package com.paulbaker.cookpad.data.datasource.remote


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("user")
    val user: User?
) {
    data class User(
        @SerializedName("about")
        val about: String?,
        @SerializedName("address")
        val address: String?,
        @SerializedName("avatar")
        val avatar: String?,
        @SerializedName("cookpadId")
        val cookpadId: String?,
        @SerializedName("_id")
        val id: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("password")
        val password: String?,
        @SerializedName("role")
        val role: Int?,
        @SerializedName("username")
        val username: String?,
        @SerializedName("__v")
        val v: Int?
    )
}