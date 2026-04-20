package com.luventure.ui.chat

data class ChatUser(
    val _id: String,
    val name: String,
    val email: String
)

data class ConversationItem(
    val _id: String,
    val participants: List<ChatUser>,
    val lastMessage: String
)

data class ConversationResponse(
    val success: Boolean,
    val data: List<ConversationItem>
)