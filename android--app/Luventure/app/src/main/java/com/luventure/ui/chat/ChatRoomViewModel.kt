package com.luventure.app.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luventure.app.data.remote.RetrofitClient
import com.luventure.data.remote.SocketManager
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

    private var roomId = ""

    fun load(token: String, id: String) {
        viewModelScope.launch {
            try {
                val res =
                    RetrofitClient.api.getMessages(
                        "Bearer $token",
                        id
                    )

                if (res.isSuccessful && res.body() != null) {
                    _messages.value = res.body()!!.data
                }

            } catch (_: Exception) {
            }
        }
    }

    fun connectRoom(conversationId: String) {

        roomId = conversationId

        val socket = SocketManager.getSocket()

        SocketManager.connect()

        socket.emit("join_room", conversationId)

        socket.off("new_message")

        socket.on("new_message") { args ->

            viewModelScope.launch {

                try {
                    val obj = args[0] as JSONObject

                    val msg = MessageItem(
                        _id = obj.getString("_id"),
                        sender = obj.getString("sender"),
                        text = obj.getString("text")
                    )

                    _messages.value += msg

                } catch (e: Exception) {
                    e.printStackTrace()
                }
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
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun disconnectRoom() {

        val socket = SocketManager.getSocket()

        if (roomId.isNotBlank()) {
            socket.emit("leave_room", roomId)
        }

        socket.off("new_message")
    }

    override fun onCleared() {
        disconnectRoom()
        SocketManager.disconnect()
        super.onCleared()
    }
}