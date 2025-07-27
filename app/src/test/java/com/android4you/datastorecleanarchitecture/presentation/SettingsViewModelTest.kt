package com.android4you.datastorecleanarchitecture.presentation

import app.cash.turbine.test
import com.android4you.datastorecleanarchitecture.data.repository.FakeSettingsRepository
import com.android4you.datastorecleanarchitecture.domain.usecases.GetUserSettingsUseCase
import com.android4you.datastorecleanarchitecture.domain.usecases.UpdateDarkModeUseCase
import com.android4you.datastorecleanarchitecture.domain.usecases.UpdateLanguageUseCase
import com.android4you.datastorecleanarchitecture.domain.usecases.UpdateUsernameUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val repository = FakeSettingsRepository()
    private val get = GetUserSettingsUseCase(repository)
    private val updateDark = UpdateDarkModeUseCase(repository)
    private val updateLang = UpdateLanguageUseCase(repository)
    private val updateName = UpdateUsernameUseCase(repository)
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SettingsViewModel(get, updateDark, updateLang, updateName)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `toggle dark mode updates setting`() = runTest {

        viewModel.settings.test {
            skipItems(1)
            viewModel.onDarkModeChange(true)
            advanceUntilIdle()
            assertTrue( awaitItem().darkMode)
        }

    }

    @Test
    fun `update language changes value`() = runTest {
        viewModel.settings.test {
            skipItems(1)
            viewModel.onLanguageChange("hi")
            advanceUntilIdle()
            assertEquals("hi", awaitItem().language)
        }
    }

    @Test
    fun `update username changes value`() = runTest {
        viewModel.settings.test {
            skipItems(1)
            viewModel.onUsernameChange("Manu")
            advanceUntilIdle()
            assertEquals("Manu", awaitItem().username)
        }
    }
}
