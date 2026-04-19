package com.luventure.app.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luventure.app.data.remote.RegisterRequest
import com.luventure.app.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _success = MutableStateFlow(false)
    val success = _success.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    fun register(
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {

            _loading.value = true

            try {
                val response =
                    RetrofitClient.api.register(
                        RegisterRequest(
                            name,
                            email,
                            password
                        )
                    )

                if (
                    response.isSuccessful &&
                    response.body()?.success == true
                ) {
                    _success.value = true
                } else {
                    _error.value =
                        response.body()?.message
                            ?: "Register failed"
                }

            } catch (e: Exception) {
                _error.value =
                    e.localizedMessage ?: "Error"
            }

            _loading.value = false
        }
    }
}