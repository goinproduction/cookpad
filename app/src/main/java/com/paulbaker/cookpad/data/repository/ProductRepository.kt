package com.paulbaker.cookpad.data.repository

import com.paulbaker.cookpad.core.network.ProductApi
import com.paulbaker.cookpad.core.network.UserApi
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productApi: ProductApi) {
    suspend fun getAllRecipes() = productApi.getAllRecipes()
}