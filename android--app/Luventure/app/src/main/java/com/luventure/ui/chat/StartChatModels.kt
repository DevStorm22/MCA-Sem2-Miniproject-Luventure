package com.luventure.ui.chat

data class StartChatConversation(
    val _id: String
)

data class StartChatResponse(
    val success: Boolean,
    val data: StartChatConversation
)