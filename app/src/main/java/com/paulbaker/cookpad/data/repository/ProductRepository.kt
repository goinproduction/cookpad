package com.paulbaker.cookpad.data.repository

import com.paulbaker.cookpad.core.network.ProductApi
import com.paulbaker.cookpad.data.datasource.local.CartRecipesModel
import com.paulbaker.cookpad.data.datasource.local.CreateRecipesModel
import com.paulbaker.cookpad.data.datasource.local.Payload
import retrofit2.http.Body
import retrofit2.http.Path
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productApi: ProductApi) {
    suspend fun getAllRecipes() = productApi.getAllRecipes()
    suspend fun createRecipe(userId: String, recipes: CreateRecipesModel) =
        productApi.createRecipe(userId, recipes)

    suspend fun editRecipeLike(userId: String, likes: String) =
        productApi.editRecipeLike(userId, likes)

    suspend fun searchFood(payload: Payload) = productApi.searchFood(payload)

    suspend fun getCartUser(userId: String) = productApi.getCartUser(userId)

    suspend fun updateCartUser(userId: String, cartRegisterResponse: CartRecipesModel) =
        productApi.updateCartUser(userId, cartRegisterResponse)
}