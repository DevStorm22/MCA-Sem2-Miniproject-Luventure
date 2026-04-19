package com.luventure.app.ui.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.luventure.app.data.local.SessionManager

@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val vm: LoginViewModel = viewModel()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loading by vm.loading.collectAsState()
    val success by vm.success.collectAsState()
    val error by vm.error.collectAsState()

    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val token by vm.token.collectAsState()

    LaunchedEffect(success) {
        if (success) {
            sessionManager.saveToken(token)
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login")

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                vm.login(email, password)
            },
            enabled = !loading
        ) {
            Text(if (loading) "Loading..." else "Login")
        }

        if (error.isNotBlank()) {
            Text(error)
        }

        TextButton(onClick = onRegisterClick) {
            Text("Create Account")
        }
    }
}