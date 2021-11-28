package com.paulbaker.cookpad.data.repository

import com.paulbaker.cookpad.core.network.UserApi
import com.paulbaker.cookpad.data.datasource.local.RegisterUser
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi){
    suspend fun registerUser(user: RegisterUser) = userApi.registerUser(user)
    suspend fun loginUser(user: RegisterUser) = userApi.login(user)
    suspend fun getProfile() = userApi.getProfile()
}