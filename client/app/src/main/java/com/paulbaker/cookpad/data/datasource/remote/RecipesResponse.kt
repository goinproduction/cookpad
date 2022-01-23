package com.paulbaker.cookpad.data.datasource.remote


import com.google.gson.annotations.SerializedName

data class RecipesResponse(
    @SerializedName("data")
    val data: List<Data>?,
    @SerializedName("success")
    val success: Boolean?
) {

}