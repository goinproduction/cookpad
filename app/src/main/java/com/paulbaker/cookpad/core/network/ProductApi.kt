package com.paulbaker.cookpad.core.network

import com.paulbaker.cookpad.data.datasource.local.CreateRecipesModel
import com.paulbaker.cookpad.data.datasource.remote.RecipesResponse
import com.paulbaker.cookpad.data.datasource.remote.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApi {
    @GET("api/recipe/getAllRecipes")
    suspend fun getAllRecipes(): Response<RecipesResponse>

    @POST("api/recipe/createRecipe/{user_id}")
    suspend fun createRecipe(
        @Path(value = "user_id", encoded = true) userId: String,
        @Body recipes: CreateRecipesModel
    ): Response<RegisterResponse>
}