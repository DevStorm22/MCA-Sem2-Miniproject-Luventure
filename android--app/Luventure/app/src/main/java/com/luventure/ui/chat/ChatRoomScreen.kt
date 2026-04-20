package com.luventure.app.ui.chat

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
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment

@Composable
fun ChatRoomScreen(
    conversationId: String,
    currentUserId: String
) {
    val context = LocalContext.current
    val session = SessionManager(context)

    val vm: ChatRoomViewModel = viewModel()

    val messages by vm.messages.collectAsState()

    var text by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {

        session.getToken()?.let { token ->

            while (true) {

                vm.load(
                    token,
                    conversationId
                )

                delay(3000)
            }
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

                val isMine = msg.sender == currentUserId

                Row(
                    modifier = Modifier.fillMaxWidth(),
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

                Spacer(Modifier.height(8.dp))
            }
        }

        Spacer(Modifier.height(8.dp))

        Surface(
            tonalElevation = 3.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.weight(1f),
                    placeholder = {
                        Text("Message...")
                    }
                )

                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (text.isNotBlank()) {
                            session.getToken()?.let {
                                vm.send(
                                    it,
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
}