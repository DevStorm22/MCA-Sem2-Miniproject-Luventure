package com.luventure.ui.chat

import androidx.compose.foundation.clickable
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
fun ChatListScreen(
    onOpenChat: (String) -> Unit
) {
    val context = LocalContext.current
    val session = SessionManager(context)

    val vm: ChatViewModel = viewModel()

    val chats by vm.items.collectAsState()
    val loading by vm.loading.collectAsState()

    LaunchedEffect(Unit) {
        session.getToken()?.let {
            vm.load(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Chats")

        Spacer(Modifier.height(16.dp))

        if (loading) {
            Text("Loading...")
        } else if (chats.isEmpty()) {
            Text("No conversations found")
        } else {
            LazyColumn {
                items(chats) { item ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .clickable {
                                onOpenChat(item._id)
                            }
                    ) {
                        Column(
                            modifier =
                                Modifier.padding(16.dp)
                        ) {

                            Text(
                                item.participants
                                    .firstOrNull()
                                    ?.name ?: "User"
                            )

                            Spacer(
                                Modifier.height(6.dp)
                            )

                            Text(
                                item.lastMessage.ifBlank {
                                    "Start conversation"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}