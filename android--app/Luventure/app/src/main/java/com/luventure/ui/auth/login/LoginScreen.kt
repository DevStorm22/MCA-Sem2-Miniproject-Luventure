package com.luventure.ui.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luventure.app.R
import com.luventure.app.data.local.SessionManager
import com.luventure.app.ui.auth.login.LoginViewModel
import com.luventure.app.utils.Validators

@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {

    val vm: LoginViewModel = viewModel()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var localError by remember { mutableStateOf("") }

    val loading by vm.loading.collectAsState()
    val success by vm.success.collectAsState()
    val token by vm.token.collectAsState()
    val userId by vm.userId.collectAsState()
    val error by vm.error.collectAsState()

    val context = LocalContext.current
    val session = SessionManager(context)

    LaunchedEffect(success, token, userId) {
        if (success && token.isNotBlank()) {
            session.saveToken(token)
            session.saveUserId(userId)
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                when {
                    email.isBlank() -> localError = "Email required"
                    !Validators.isValidEmail(email) -> localError = "Invalid email"
                    password.isBlank() -> localError = "Password required"
                    else -> {
                        localError = ""
                        vm.login(email.trim(), password)
                    }
                }
            },
            enabled = !loading
        ) {
            Text(if (loading) "Loading..." else "Login")
        }

        if (localError.isNotBlank()) Text(localError)
        if (error.isNotBlank()) Text(error)

        TextButton(onClick = onRegisterClick) {
            Text("Create Account")
        }
    }
}