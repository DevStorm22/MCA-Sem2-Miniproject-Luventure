package com.luventure.ui.discover

data class DiscoverUser(
    val _id: String,
    val name: String,
    val email: String
)

data class DiscoverResponse(
    val success: Boolean,
    val data: List<DiscoverUser>
)