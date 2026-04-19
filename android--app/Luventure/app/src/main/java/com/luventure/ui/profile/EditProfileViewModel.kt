package com.luventure.app.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luventure.app.data.remote.RetrofitClient
import com.luventure.app.data.remote.UpdateProfileRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditProfileViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _success = MutableStateFlow(false)
    val success = _success.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    fun update(
        token: String,
        name: String
    ) {
        viewModelScope.launch {

            _loading.value = true

            try {
                val response =
                    RetrofitClient.api.updateProfile(
                        "Bearer $token",
                        UpdateProfileRequest(name)
                    )

                if (
                    response.isSuccessful &&
                    response.body()?.success == true
                ) {
                    _success.value = true
                } else {
                    _error.value =
                        response.body()?.message
                            ?: "Update failed"
                }

            } catch (e: Exception) {
                _error.value =
                    e.localizedMessage ?: "Error"
            }

            _loading.value = false
        }
    }
}