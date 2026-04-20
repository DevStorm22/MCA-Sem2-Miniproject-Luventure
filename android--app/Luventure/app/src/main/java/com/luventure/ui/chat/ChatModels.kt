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

data class MessageItem(
    val _id: String,
    val sender: String,
    val text: String
)

data class MessageResponse(
    val success: Boolean,
    val data: List<MessageItem>
)

data class SendMessageRequest(
    val text: String
)