package com.luventure.app.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luventure.app.data.remote.LoginRequest
import com.luventure.app.data.remote.RetrofitClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _success = MutableStateFlow(false)
    val success = _success.asStateFlow()
    private val _error = MutableStateFlow("")
    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()
    val error = _error.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true

            try {
                val response = RetrofitClient.api.login(
                    LoginRequest(email, password)
                )

                if (response.isSuccessful &&
                    response.body()?.success == true
                ) {
                    _token.value = response.body()?.data?.token ?: ""
                    _success.value = true
                } else {
                    _error.value =
                        response.body()?.message ?: "Login failed"
                }

            } catch (e: Exception) {
                _error.value = "Network Error: ${e.localizedMessage}"
            }

            _loading.value = false
        }
    }
}