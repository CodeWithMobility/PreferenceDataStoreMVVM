package com.android4you.datastorecleanarchitecture.presentation

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android4you.datastorecleanarchitecture.domain.model.UserSettings
import com.android4you.datastorecleanarchitecture.domain.repository.ISettingsRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android4you.datastorecleanarchitecture.MainActivity
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SettingsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>() // Launches the full Activity

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun toggleDarkMode_updatesUi() {
        // The Activity already sets SettingsScreen with hiltViewModel() internally
        // No need to call setContent()

        composeRule.onNodeWithTag("dark_mode_toggle").assertIsDisplayed()
        composeRule.onNodeWithText("Dark Mode: Off").assertIsDisplayed()

        composeRule.onNodeWithTag("dark_mode_toggle").performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithText("Dark Mode: On").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithText("Dark Mode: On").assertIsDisplayed()
    }
}

class FakeSettingsRepository : ISettingsRepository {

    private val _flow = MutableStateFlow(
        UserSettings(darkMode = false, language = "en", username = "Guest")
    )

    override fun getUserSettings(): Flow<UserSettings> = _flow

    override suspend fun updateDarkMode(enabled: Boolean) {
        _flow.value = _flow.value.copy(darkMode = enabled)
    }

    override suspend fun updateLanguage(lang: String) {
        _flow.value = _flow.value.copy(language = lang)
    }

    override suspend fun updateUsername(name: String) {
        _flow.value = _flow.value.copy(username = name)
    }
}