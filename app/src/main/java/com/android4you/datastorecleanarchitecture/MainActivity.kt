package com.android4you.datastorecleanarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.android4you.datastorecleanarchitecture.presentation.SettingsScreen
import com.android4you.datastorecleanarchitecture.ui.theme.DataStoreCleanArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DataStoreCleanArchitectureTheme {
                SettingsScreen()
            }
        }
    }
}

