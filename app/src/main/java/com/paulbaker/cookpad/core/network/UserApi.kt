package com.paulbaker.cookpad.core.network

import com.paulbaker.cookpad.data.datasource.local.RegisterUser
import com.paulbaker.cookpad.data.datasource.remote.RegisterResponse
import com.paulbaker.cookpad.data.datasource.remote.UserProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST("api/user/register")
    suspend fun registerUser(@Body registerUser: RegisterUser) : Response<RegisterResponse>

    @POST("api/user/login")
    suspend fun login(@Body registerUser: RegisterUser) : Response<RegisterResponse>

    @GET("api/user/get/61879f8652370cd0a5f73e76")
    suspend fun getProfile() : Response<UserProfileResponse>
}