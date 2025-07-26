package com.android4you.datastorecleanarchitecture.presentation

import com.android4you.datastorecleanarchitecture.data.repository.FakeSettingsRepository
import com.android4you.datastorecleanarchitecture.domain.usecases.GetUserSettingsUseCase
import com.android4you.datastorecleanarchitecture.domain.usecases.UpdateDarkModeUseCase
import com.android4you.datastorecleanarchitecture.domain.usecases.UpdateLanguageUseCase
import com.android4you.datastorecleanarchitecture.domain.usecases.UpdateUsernameUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private val repo = FakeSettingsRepository()
    private val get = GetUserSettingsUseCase(repo)
    private val updateDark = UpdateDarkModeUseCase(repo)
    private val updateLang = UpdateLanguageUseCase(repo)
    private val updateName = UpdateUsernameUseCase(repo)

    private val viewModel = SettingsViewModel(get, updateDark, updateLang, updateName)

    @Test
    fun `toggle dark mode updates setting`() = runTest {
        viewModel.onDarkModeChange(true)
        val result = viewModel.settings.first()
        assertTrue(result.darkMode)
    }

    @Test
    fun `update language changes value`() = runTest {
        viewModel.onLanguageChange("hi")
        val result = viewModel.settings.first()
        assertEquals("hi", result.language)
    }

    @Test
    fun `update username changes value`() = runTest {
        viewModel.onUsernameChange("Manu")
        val result = viewModel.settings.first()
        assertEquals("Manu", result.username)
    }
}
