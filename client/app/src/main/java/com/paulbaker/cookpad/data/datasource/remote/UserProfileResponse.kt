package com.paulbaker.cookpad.data.datasource.remote


import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("success")
    val success: Boolean?
) {
    data class Data(
        @SerializedName("about")
        val about: String?,
        @SerializedName("address")
        val address: String?,
        @SerializedName("avatar")
        val avatar: String?,
        @SerializedName("cookpadId")
        val cookpadId: String?,
        @SerializedName("createdAt")
        val createdAt: String?,
        @SerializedName("_id")
        val id: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("role")
        val role: Int?
    )
}