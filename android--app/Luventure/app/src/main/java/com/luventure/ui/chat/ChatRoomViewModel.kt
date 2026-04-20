package com.luventure.app.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luventure.app.data.remote.RetrofitClient
import com.luventure.ui.chat.MessageItem
import com.luventure.ui.chat.SendMessageRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatRoomViewModel : ViewModel() {

    private val _messages =
        MutableStateFlow<List<MessageItem>>(emptyList())

    val messages = _messages.asStateFlow()

    fun load(
        token: String,
        id: String
    ) {
        viewModelScope.launch {
            try {
                val res =
                    RetrofitClient.api.getMessages(
                        "Bearer $token",
                        id
                    )

                if (
                    res.isSuccessful &&
                    res.body() != null
                ) {
                    _messages.value =
                        res.body()!!.data
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun send(
        token: String,
        id: String,
        text: String
    ) {
        viewModelScope.launch {
            try {
                RetrofitClient.api.sendMessage(
                    "Bearer $token",
                    id,
                    SendMessageRequest(text)
                )

                load(token, id)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}