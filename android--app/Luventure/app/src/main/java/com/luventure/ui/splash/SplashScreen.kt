package com.luventure.app.ui.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.luventure.app.data.local.SessionManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onGoLogin: () -> Unit,
    onGoHome: () -> Unit
) {
    val context = LocalContext.current
    val session = SessionManager(context)

    LaunchedEffect(Unit) {
        delay(1500)

        val token = session.getToken()

        if (token.isNullOrBlank()) {
            onGoLogin()
        } else {
            onGoHome()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Luventure", fontSize = 32.sp)
    }
}