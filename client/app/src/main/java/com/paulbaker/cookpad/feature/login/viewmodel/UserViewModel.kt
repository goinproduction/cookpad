package com.paulbaker.cookpad.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.paulbaker.cookpad.core.extensions.Resource
import com.paulbaker.cookpad.data.datasource.local.RegisterUser
import com.paulbaker.cookpad.data.datasource.local.UpdateUser
import com.paulbaker.cookpad.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.http.Body
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    fun registerUser(user: RegisterUser) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.registerUser(user)))
        } catch (ex: Exception) {
            emit(Resource.error(null, ex.message ?: "Error"))
        }
    }

    fun loginUser(user: RegisterUser) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.loginUser(user)))
        } catch (ex: Exception) {
            emit(Resource.error(null, ex.message ?: "Error"))
        }
    }

    fun getProfile(userId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.getProfile(userId)))
        } catch (ex: Exception) {
            emit(Resource.error(null, ex.message ?: "Error"))
        }
    }

    fun updateProfile(userId: String, updateUser: UpdateUser) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.updateProfile(userId, updateUser)))
        } catch (ex: Exception) {
            emit(Resource.error(null, ex.message ?: "Error"))
        }
    }
}