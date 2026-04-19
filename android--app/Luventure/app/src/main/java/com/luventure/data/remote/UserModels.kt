package com.luventure.app.data.remote

data class MeUser(
    val _id: String,
    val name: String,
    val email: String
)

data class MeResponse(
    val success: Boolean,
    val data: MeUser?
)