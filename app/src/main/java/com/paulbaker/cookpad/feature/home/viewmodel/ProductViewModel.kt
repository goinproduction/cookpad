package com.paulbaker.cookpad.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.paulbaker.cookpad.core.extensions.Resource
import com.paulbaker.cookpad.data.datasource.local.CreateRecipesModel
import com.paulbaker.cookpad.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {
    fun getAllRecipes() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(productRepository.getAllRecipes()))
        } catch (ex: Exception) {
            emit(Resource.error(null, ex.message ?: "Error"))
        }
    }

    fun createRecipe(userId: String, recipes: CreateRecipesModel) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(productRepository.createRecipe(userId, recipes)))
        } catch (ex: Exception) {
            emit(Resource.error(null, ex.message ?: "Error"))
        }
    }
}