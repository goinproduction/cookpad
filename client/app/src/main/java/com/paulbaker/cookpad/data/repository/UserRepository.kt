package com.paulbaker.cookpad.data.repository

import com.paulbaker.cookpad.core.network.UserApi
import com.paulbaker.cookpad.data.datasource.local.RegisterUser
import com.paulbaker.cookpad.data.datasource.local.UpdateUser
import retrofit2.http.Body
import retrofit2.http.Path
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi){
    suspend fun registerUser(user: RegisterUser) = userApi.registerUser(user)
    suspend fun loginUser(user: RegisterUser) = userApi.login(user)
    suspend fun getProfile(userId:String) = userApi.getProfile(userId)
    suspend fun updateProfile(userId :String,updateUser: UpdateUser) = userApi.updateProfile(userId,updateUser)
}