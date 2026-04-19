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
            try {
                val response =
                    RetrofitClient.api.me(
                        "Bearer $token"
                    )

                if (
                    response.isSuccessful &&
                    response.body()?.data != null
                ) {
                    _name.value =
                        response.body()!!.data!!.name

                    _email.value =
                        response.body()!!.data!!.email
                }

            } catch (_: Exception) {
            }

            _loading.value = false
        }
    }
}