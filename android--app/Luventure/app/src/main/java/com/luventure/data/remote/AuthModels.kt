package com.luventure.app.data.remote

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginUser(
    val id: String,
    val name: String,
    val email: String
)

data class LoginData(
    val token: String,
    val user: LoginUser
)

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: LoginData?
)