package com.luventure.ui.discover

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luventure.app.data.local.SessionManager

@Composable
fun DiscoverScreen() {

    val context = LocalContext.current
    val session = SessionManager(context)

    val vm: DiscoverViewModel = viewModel()

    val users by vm.users.collectAsState()

    LaunchedEffect(Unit) {
        session.getToken()?.let {
            vm.load(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        Text(
            text = "Discover People",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(12.dp))

        LazyColumn {

            items(users) { user ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text(user.name)
                        Text(user.email)

                        Spacer(
                            Modifier.height(8.dp)
                        )

                        Button(
                            onClick = { }
                        ) {
                            Text("Message")
                        }
                    }
                }
            }
        }
    }
}