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
import com.luventure.app.data.local.SessionManager
import com.luventure.app.ui.chat.ChatRoomViewModel

@Composable
fun ChatRoomScreen(
    conversationId: String,
    currentUserId: String
) {
    val context = LocalContext.current
    val session = SessionManager(context)

    val vm: ChatRoomViewModel = viewModel()
    val messages by vm.messages.collectAsState()

    var text by remember { mutableStateOf("") }

    LaunchedEffect(conversationId) {
        session.getToken()?.let { token ->
            vm.load(token, conversationId)
            vm.connectRoom(conversationId)
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

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = false
        ) {

            items(messages) { msg ->

                val isMine =
                    msg.sender.trim() ==
                            currentUserId.trim()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),

                    horizontalArrangement =
                        if (isMine)
                            Arrangement.End
                        else
                            Arrangement.Start
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

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Type message")
                }
            )

            Spacer(Modifier.width(8.dp))

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