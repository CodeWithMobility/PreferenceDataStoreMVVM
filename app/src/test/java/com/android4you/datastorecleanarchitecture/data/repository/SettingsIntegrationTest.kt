package com.android4you.datastorecleanarchitecture.data.repository

import app.cash.turbine.test
import com.android4you.datastorecleanarchitecture.domain.model.UserSettings
import com.android4you.datastorecleanarchitecture.domain.usecases.GetUserSettingsUseCase
import com.android4you.datastorecleanarchitecture.domain.usecases.UpdateDarkModeUseCase
import com.android4you.datastorecleanarchitecture.domain.usecases.UpdateLanguageUseCase
import com.android4you.datastorecleanarchitecture.domain.usecases.UpdateUsernameUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class SettingsIntegrationTest {

    private lateinit var repository: FakeSettingsRepository

    private lateinit var updateDarkMode: UpdateDarkModeUseCase
    private lateinit var updateLanguage: UpdateLanguageUseCase
    private lateinit var updateUsername: UpdateUsernameUseCase
    private lateinit var getUserSettings: GetUserSettingsUseCase

    @Before
    fun setup() {
        repository = FakeSettingsRepository()
        updateDarkMode = UpdateDarkModeUseCase(repository)
        updateLanguage = UpdateLanguageUseCase(repository)
        updateUsername = UpdateUsernameUseCase(repository)
        getUserSettings = GetUserSettingsUseCase(repository)
    }

    @Test
    fun `settings should update sequentially and emit correct states`() = runTest {
        val flow = getUserSettings()

        flow.test {
            // 1. Initial state
            val initial = awaitItem()
            assertEquals(UserSettings(darkMode = false, language = "en", username = "Guest"), initial) // default values

            // 2. Update dark mode
            updateDarkMode(true)
            val darkModeUpdated = awaitItem()
            assertEquals(true, darkModeUpdated.darkMode)

            // 3. Update language
            updateLanguage("fr")
            val languageUpdated = awaitItem()
            assertEquals("fr", languageUpdated.language)
            assertEquals(true, languageUpdated.darkMode)

            // 4. Update username
            updateUsername("Manu")
            val usernameUpdated = awaitItem()
            assertEquals("Manu", usernameUpdated.username)
            assertEquals("fr", usernameUpdated.language)
            assertEquals(true, usernameUpdated.darkMode)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
