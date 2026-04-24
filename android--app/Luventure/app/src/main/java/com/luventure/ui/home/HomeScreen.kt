package com.luventure.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.luventure.app.data.local.SessionManager
import com.luventure.app.R

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onEditProfile: () -> Unit,
    onOpenChats: () -> Unit,
    onOpenDiscover: () -> Unit
) {
    val context = LocalContext.current
    val session = SessionManager(context)

    val vm: HomeViewModel = viewModel()

    val name by vm.name.collectAsState()
    val email by vm.email.collectAsState()
    val loading by vm.loading.collectAsState()

    LaunchedEffect(Unit) {
        val token = session.getToken()
        println("HOME TOKEN = $token")

        if (!token.isNullOrBlank()) {
            vm.loadProfile(token)
        } else {
            println("TOKEN MISSING")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(120.dp)
        )

        if (loading) {
            Text("Loading profile...")
        } else {
            Text(
                text = "Hello, $name",
                fontSize = 28.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = email,
                fontSize = 16.sp
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onEditProfile,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Edit Profile")
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = onOpenChats,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Chat")
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = onOpenDiscover,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Discover People")
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                session.clearSession()
                onLogout()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    desc: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(text = desc)
        }
    }
}
