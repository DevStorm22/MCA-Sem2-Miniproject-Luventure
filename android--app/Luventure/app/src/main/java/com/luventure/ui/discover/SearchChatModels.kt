package com.luventure.ui.discover

data class StartChatConversation(
    val _id: String
)

data class StartChatResponse(
    val success: Boolean,
    val data: StartChatConversation
)