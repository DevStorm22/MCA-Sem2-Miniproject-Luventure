package com.luventure.app.ui.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luventure.app.utils.Validators
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun RegisterScreen(
    onBackToLogin: () -> Unit
) {
    val vm: RegisterViewModel = viewModel()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loading by vm.loading.collectAsState()
    val success by vm.success.collectAsState()
    val error by vm.error.collectAsState()

    var localError by remember {
        mutableStateOf("")
    }

    LaunchedEffect(success) {
        if (success) {
            onBackToLogin()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Register")

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

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
            label = { Text("Password") },
            visualTransformation =
                PasswordVisualTransformation()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                when {
                    !Validators.isValidName(name) ->
                        localError = "Name too short"

                    email.isBlank() ->
                        localError = "Email required"

                    !Validators.isValidEmail(email) ->
                        localError = "Invalid email"

                    !Validators.isValidPassword(password) ->
                        localError = "Password must be 6+ chars"

                    else -> {
                        localError = ""
                        vm.register(
                            name.trim(),
                            email.trim(),
                            password
                        )
                    }
                }
            },
            enabled = !loading
        ) {
            Text(
                if (loading)
                    "Creating..."
                else
                    "Create Account"
            )
        }

        if (error.isNotBlank()) {
            Text(error)
        }

        TextButton(
            onClick = onBackToLogin
        ) {
            Text("Back to Login")
        }
    }
}