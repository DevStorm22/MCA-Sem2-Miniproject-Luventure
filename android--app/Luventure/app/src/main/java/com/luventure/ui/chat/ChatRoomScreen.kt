package com.luventure.ui.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luventure.data.local.SessionManager
import com.luventure.app.ui.chat.ChatRoomViewModel
import androidx.activity.compose.BackHandler
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun ChatRoomScreen(
    conversationId: String,
    currentUserId: String,
    onBack: () -> Unit
) {

    val context = LocalContext.current
    val session = SessionManager(context)

    val vm: ChatRoomViewModel = viewModel()
    val messages by vm.messages.collectAsState()

    val focusManager = LocalFocusManager.current

    var text by remember { mutableStateOf("") }

    BackHandler {
        focusManager.clearFocus(force = true)
        onBack()
    }

    LaunchedEffect(conversationId) {
        session.getToken()?.let { token ->
            vm.load(token, conversationId)
            vm.connectRoom(conversationId)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            vm.disconnectRoom()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        Text(
            text = "Chat",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(messages) { msg ->

                val isMine =
                    msg.sender.trim()
                        .equals(currentUserId.trim(), true)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),

                    horizontalArrangement =
                        if (isMine) Arrangement.End
                        else Arrangement.Start
                ) {

                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        tonalElevation = 2.dp
                    ) {
                        Text(
                            text = msg.text,
                            modifier = Modifier.padding(
                                horizontal = 14.dp,
                                vertical = 10.dp
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        session.getToken()?.let { token ->
                            vm.send(
                                token,
                                conversationId,
                                text.trim()
                            )
                            text = ""
                        }
                    }
                }
            ) {
                Text("Send")
            }
        }
    }
}