package com.paulbaker.cookpad.core.network

import com.paulbaker.cookpad.data.datasource.local.RegisterUser
import com.paulbaker.cookpad.data.datasource.local.UpdateUser
import com.paulbaker.cookpad.data.datasource.remote.LoginResponse
import com.paulbaker.cookpad.data.datasource.remote.RegisterResponse
import com.paulbaker.cookpad.data.datasource.remote.UserProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("api/user/register")
    suspend fun registerUser(@Body registerUser: RegisterUser): Response<RegisterResponse>

    @POST("api/user/login")
    suspend fun login(@Body registerUser: RegisterUser): Response<LoginResponse>

    @GET("api/user/get/{user_id}")
    suspend fun getProfile(
        @Path(
            value = "user_id",
            encoded = true
        ) userId: String
    ): Response<UserProfileResponse>

    @POST("api/user/edit/{user_id}")
    suspend fun updateProfile(
        @Path(value = "user_id", encoded = true) userId: String,
        @Body updateUser: UpdateUser
    ): Response<UserProfileResponse>
}