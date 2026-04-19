package com.luventure.app.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.luventure.app.data.local.SessionManager

@Composable
fun HomeScreen(
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val session = SessionManager(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Welcome to Luventure",
            fontSize = 28.sp
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Build meaningful connections.",
            fontSize = 16.sp
        )

        Spacer(Modifier.height(24.dp))

        DashboardCard(
            title = "Start Chat",
            desc = "Messaging module coming soon."
        )

        Spacer(Modifier.height(12.dp))

        DashboardCard(
            title = "Edit Profile",
            desc = "Manage your account details."
        )

        Spacer(Modifier.height(12.dp))

        DashboardCard(
            title = "Discover People",
            desc = "Find like-minded users soon."
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                session.clearToken()
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
            Spacer(Modifier.height(6.dp))
            Text(desc)
        }
    }
}