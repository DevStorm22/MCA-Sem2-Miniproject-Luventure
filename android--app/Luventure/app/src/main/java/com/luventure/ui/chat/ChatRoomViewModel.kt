package com.luventure.app.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luventure.app.data.remote.RetrofitClient
import com.luventure.app.data.remote.SocketManager
import com.luventure.ui.chat.MessageItem
import com.luventure.ui.chat.SendMessageRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

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

            } catch (_: Exception) {
            }
        }
    }

    fun connectRoom(
        conversationId: String
    ) {
        val socket =
            SocketManager.getSocket()

        SocketManager.connect()

        socket.emit(
            "join_room",
            conversationId
        )

        socket.off("new_message")

        socket.on("new_message") { args ->

            try {
                val obj =
                    args[0] as JSONObject

                val msg =
                    MessageItem(
                        _id =
                            obj.getString("_id"),
                        sender =
                            obj.getString("sender"),
                        text =
                            obj.getString("text")
                    )

                _messages.value =
                    _messages.value + msg

            } catch (_: Exception) {
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

            } catch (_: Exception) {
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        SocketManager.disconnect()
    }
}