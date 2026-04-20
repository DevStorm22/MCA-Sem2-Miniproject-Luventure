package com.luventure.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luventure.app.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _items =
        MutableStateFlow<List<ConversationItem>>(emptyList())

    val items = _items.asStateFlow()

    private val _loading =
        MutableStateFlow(true)

    val loading = _loading.asStateFlow()

    fun load(token: String) {
        viewModelScope.launch {
            try {
                val res =
                    RetrofitClient.api
                        .getConversations(
                            "Bearer $token"
                        )

                if (
                    res.isSuccessful &&
                    res.body() != null
                ) {
                    _items.value =
                        res.body()!!.data
                }

            } catch (_: Exception) {
            }

            _loading.value = false
        }
    }
}