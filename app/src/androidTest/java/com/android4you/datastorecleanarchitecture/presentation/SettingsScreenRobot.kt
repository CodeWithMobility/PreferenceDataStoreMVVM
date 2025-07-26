package com.android4you.datastorecleanarchitecture.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput

class SettingsScreenRobot(private val rule: ComposeTestRule) {

    fun assertDarkModeText(isOn: Boolean): SettingsScreenRobot {
        val text = if (isOn) "Dark Mode: On" else "Dark Mode: Off"
        rule.onNodeWithText(text).assertIsDisplayed()
        return this
    }

    fun toggleDarkMode(): SettingsScreenRobot {
        rule.onNodeWithTag("dark_mode_toggle").performClick()
        return this
    }

    fun assertLanguage(language: String): SettingsScreenRobot {
        rule.onNodeWithText("Language: $language").assertIsDisplayed()
        return this
    }

    fun updateLanguage(newLang: String): SettingsScreenRobot {
        rule.onNodeWithTag("language_input").performTextClearance()
        rule.onNodeWithTag("language_input").performTextInput(newLang)
        return this
    }

    fun assertUsername(name: String): SettingsScreenRobot {
        rule.onNodeWithText("Username: $name").assertIsDisplayed()
        return this
    }

    fun updateUsername(name: String): SettingsScreenRobot {
        rule.onNodeWithTag("username_input").performTextClearance()
        rule.onNodeWithTag("username_input").performTextInput(name)
        return this
    }
}
