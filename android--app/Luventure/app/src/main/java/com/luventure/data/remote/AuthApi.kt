package com.luventure.data.remote

import com.luventure.app.data.remote.BasicResponse
import com.luventure.app.data.remote.LoginRequest
import com.luventure.app.data.remote.LoginResponse
import com.luventure.app.data.remote.MeResponse
import com.luventure.app.data.remote.RegisterRequest
import com.luventure.app.data.remote.UpdateProfileRequest
import com.luventure.ui.chat.ConversationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
interface AuthApi {

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("api/user/me")
    suspend fun me(
        @Header("Authorization") token: String
    ): Response<MeResponse>

    @POST("api/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<BasicResponse>

    @PATCH("api/user/update-profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateProfileRequest
    ): Response<BasicResponse>

    @GET("api/chat/conversations")
    suspend fun getConversations(
        @Header("Authorization") token: String
    ): Response<ConversationResponse>
}