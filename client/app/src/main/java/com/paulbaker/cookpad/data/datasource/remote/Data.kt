package com.paulbaker.cookpad.data.datasource.remote

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("author")
    val author: Author?,
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
    val steps: List<Step>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("__v")
    val v: Int?
) {
    data class Author(
        @SerializedName("avatar")
        val avatar: String?,
        @SerializedName("_id")
        val id: String?,
        @SerializedName("name")
        val name: String?
    )

    data class Step(
        @SerializedName("_id")
        val id: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("picture")
        val picture: String?
    )
}