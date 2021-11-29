package com.paulbaker.cookpad.core.network

import com.paulbaker.cookpad.data.datasource.remote.RecipesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {
    @GET("api/recipe/getAllRecipes")
    suspend fun getAllRecipes() : Response<RecipesResponse>
}