package com.luventure.data.remote

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginUser(

    @SerializedName(value = "_id", alternate = ["id"])
    val id: String = "",

    val name: String = "",
    val email: String = ""
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

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class BasicResponse(
    val success: Boolean,
    val message: String
)

data class UpdateProfileRequest(
    val name: String
)