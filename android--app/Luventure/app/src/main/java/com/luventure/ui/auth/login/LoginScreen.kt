package com.luventure.app.ui.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onLoginSuccess) {
            Text("Temporary Login")
        }

        TextButton(onClick = onRegisterClick) {
            Text("Create Account")
        }
    }
}