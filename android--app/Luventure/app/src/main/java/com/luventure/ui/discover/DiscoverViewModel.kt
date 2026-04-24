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
}