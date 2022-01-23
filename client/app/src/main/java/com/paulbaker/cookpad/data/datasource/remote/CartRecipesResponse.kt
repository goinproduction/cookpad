package com.paulbaker.cookpad.data.datasource.remote


import com.google.gson.annotations.SerializedName

data class CartRecipesResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
) {
    data class Data(
        @SerializedName("_id")
        val id: String?,
        @SerializedName("recipes")
        val recipes: List<Recipe>?,
        @SerializedName("userId")
        val userId: String?,
        @SerializedName("__v")
        val v: Int?
    ) {
        data class Recipe(
            @SerializedName("author")
            val author: String?,
            @SerializedName("category")
            val category: String?,
            @SerializedName("claps")
            val claps: Int?,
            @SerializedName("cookTime")
            val cookTime: Int?,
            @SerializedName("dateCreate")
            val dateCreate: String?,
            @SerializedName("description")
            val description: String?,
            @SerializedName("hearts")
            val hearts: Int?,
            @SerializedName("_id")
            val id: String?,
            @SerializedName("image")
            val image: String?,
            @SerializedName("ingredients")
            val ingredients: List<String>?,
            @SerializedName("likes")
            val likes: Int?,
            @SerializedName("origin")
            val origin: String?,
            @SerializedName("serves")
            val serves: Int?,
            @SerializedName("steps")
            val steps: List<com.paulbaker.cookpad.data.datasource.remote.Data.Step>?,
            @SerializedName("title")
            val title: String?,
            @SerializedName("__v")
            val v: Int?
        ) {
        }
    }
}