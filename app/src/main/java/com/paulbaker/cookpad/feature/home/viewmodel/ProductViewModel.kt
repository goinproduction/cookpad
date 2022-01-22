package com.paulbaker.cookpad.feature.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.paulbaker.cookpad.core.extensions.Resource
import com.paulbaker.cookpad.data.datasource.local.CartRecipesModel
import com.paulbaker.cookpad.data.datasource.local.CreateRecipesModel
import com.paulbaker.cookpad.data.datasource.remote.Data
import com.paulbaker.cookpad.data.datasource.local.Payload
import com.paulbaker.cookpad.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {
    val productItem = MutableLiveData<Data>()
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

    fun editRecipeLike(userId: String, likes: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(productRepository.editRecipeLike(userId, likes)))
        } catch (ex: Exception) {
            emit(Resource.error(null, ex.message ?: "Error"))
        }
    }

    fun searchFood(payload: Payload) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(productRepository.searchFood(payload)))
        } catch (ex: Exception) {
            emit(Resource.error(null, ex.message ?: "Error"))
        }
    }

    fun getCartUser(userId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(productRepository.getCartUser(userId)))
        } catch (ex: Exception) {
            emit(Resource.error(null, ex.message ?: "Error"))
        }
    }


    fun updateCartUser(userId: String, cartRecipesModel: CartRecipesModel) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(productRepository.updateCartUser(userId,cartRecipesModel)))
        } catch (ex: Exception) {
            emit(Resource.error(null, ex.message ?: "Error"))
        }
    }

    fun setProductItem(item: Data) {
        this.productItem.value = item
    }

}