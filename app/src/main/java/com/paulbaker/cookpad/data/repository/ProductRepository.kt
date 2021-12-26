package com.paulbaker.cookpad.data.repository

import com.paulbaker.cookpad.core.network.ProductApi
import com.paulbaker.cookpad.data.datasource.local.CreateRecipesModel
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productApi: ProductApi) {
    suspend fun getAllRecipes() = productApi.getAllRecipes()
    suspend fun createRecipe(userId: String, recipes: CreateRecipesModel) =
        productApi.createRecipe(userId, recipes)
}