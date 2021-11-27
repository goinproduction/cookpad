package com.paulbaker.cookpad.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.paulbaker.cookpad.core.extensions.Resource
import com.paulbaker.cookpad.data.datasource.local.RegisterUser
import com.paulbaker.cookpad.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    fun getProfile() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepository.getProfile()))
        } catch (ex: Exception) {
            emit(Resource.error(null, ex.message ?: "Error"))
        }
    }
}