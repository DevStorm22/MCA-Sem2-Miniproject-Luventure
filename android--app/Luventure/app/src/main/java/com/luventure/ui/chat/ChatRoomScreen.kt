package com.luventure.app.ui.chat

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
fun ChatRoomScreen(
    conversationId: String
) {
    val context = LocalContext.current
    val session = SessionManager(context)

    val vm: ChatRoomViewModel = viewModel()

    val messages by vm.messages.collectAsState()

    var text by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        session.getToken()?.let {
            vm.load(it, conversationId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        Text("Chat")

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(messages) { msg ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = msg.text,
                        modifier =
                            Modifier.padding(12.dp)
                    )
                }
            }
        }

        Row {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(8.dp))

            Button(
                onClick = {
                    session.getToken()?.let {
                        vm.send(
                            it,
                            conversationId,
                            text
                        )
                        text = ""
                    }
                }
            ) {
                Text("Send")
            }
        }
    }
}