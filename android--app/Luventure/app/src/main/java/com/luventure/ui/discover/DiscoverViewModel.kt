package com.luventure.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luventure.app.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiscoverViewModel : ViewModel() {

    private val _users =
        MutableStateFlow<List<DiscoverUser>>(emptyList())
    val users = _users.asStateFlow()

    private val _chatId =
        MutableStateFlow("")
    val chatId = _chatId.asStateFlow()

    fun load(token: String) {
        viewModelScope.launch {
            try {
                val res =
                    RetrofitClient.api
                        .discoverUsers("Bearer $token")

                if (res.isSuccessful &&
                    res.body() != null
                ) {
                    _users.value =
                        res.body()!!.data
                }

            } catch (_: Exception) {
            }
        }
    }

    fun startChat(
        token: String,
        userId: String
    ) {
        viewModelScope.launch {
            try {
                val res =
                    RetrofitClient.api.startChat(
                        "Bearer $token",
                        userId
                    )

                if (res.isSuccessful &&
                    res.body() != null
                ) {
                    _chatId.value =
                        res.body()!!.data._id
                }

            } catch (_: Exception) {
            }
        }
    }
}