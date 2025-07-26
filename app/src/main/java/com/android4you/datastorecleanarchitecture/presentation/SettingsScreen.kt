package com.android4you.datastorecleanarchitecture.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val state by viewModel.settings.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(56.dp))
            Text(text = "Dark Mode: ${if (state.darkMode) "On" else "Off"}")
            Button(
                onClick = { viewModel.onDarkModeChange(!state.darkMode) },
                modifier = Modifier.testTag("dark_mode_toggle")
            ) {
                Text("Toggle Dark Mode")
            }

            Spacer(Modifier.height(16.dp))
            Text(text = "Language: ${state.language}")
            OutlinedTextField(
                value = state.language,
                onValueChange = { viewModel.onLanguageChange(it) },
                modifier = Modifier.testTag("language_input"),
                label = { Text("Language") }
            )

            Spacer(Modifier.height(16.dp))
            Text(text = "Username: ${state.username}")
            OutlinedTextField(
                value = state.username,
                onValueChange = { viewModel.onUsernameChange(it) },
                modifier = Modifier.testTag("username_input"),
                label = { Text("Username") }
            )
        }
    }

}
