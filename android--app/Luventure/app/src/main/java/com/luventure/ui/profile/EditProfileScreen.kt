package com.luventure.app.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luventure.app.data.local.SessionManager

@Composable
fun EditProfileScreen(
    onSaved: () -> Unit
) {
    val context = LocalContext.current
    val session = SessionManager(context)

    val vm: EditProfileViewModel = viewModel()

    var name by remember {
        mutableStateOf("")
    }

    val loading by vm.loading.collectAsState()
    val success by vm.success.collectAsState()
    val error by vm.error.collectAsState()

    LaunchedEffect(success) {
        if (success) onSaved()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text("Edit Profile")

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("New Name") }
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                session.getToken()?.let {
                    vm.update(it, name.trim())
                }
            },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (loading)
                    "Saving..."
                else
                    "Save Changes"
            )
        }

        if (error.isNotBlank()) {
            Spacer(Modifier.height(12.dp))
            Text(error)
        }
    }
}