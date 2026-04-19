package com.luventure.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luventure.app.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    fun loadProfile(token: String) {
        viewModelScope.launch {
            _loading.value = true

            try {
                val response =
                    RetrofitClient.api.me("Bearer $token")

                println("DEBUG RESPONSE: ${response.code()}")

                if (response.isSuccessful) {

                    val body = response.body()
                    println("DEBUG BODY: $body")

                    val data = body?.data

                    if (data != null) {
                        _name.value = data.name ?: "No Name"
                        _email.value = data.email ?: "No Email"
                    } else {
                        _name.value = "NULL DATA"
                        _email.value = ""
                    }

                } else {
                    _name.value = "API ERROR ${response.code()}"
                    _email.value = ""
                }

            } catch (e: Exception) {
                _name.value = "Exception"
                _email.value = e.message ?: "error"
                e.printStackTrace()
            }

            _loading.value = false
        }
    }
}