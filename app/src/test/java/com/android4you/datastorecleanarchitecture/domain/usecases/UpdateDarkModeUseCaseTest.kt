package com.android4you.datastorecleanarchitecture.domain.usecases

import app.cash.turbine.test
import com.android4you.datastorecleanarchitecture.data.repository.FakeSettingsRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateDarkModeUseCaseTest {

    private lateinit var fakeRepository: FakeSettingsRepository
    private lateinit var updateDarkModeUseCase: UpdateDarkModeUseCase
    private lateinit var getUserSettingsUseCase: GetUserSettingsUseCase

    @Before
    fun setup() {
        fakeRepository = FakeSettingsRepository()
        updateDarkModeUseCase = UpdateDarkModeUseCase(fakeRepository)
        getUserSettingsUseCase = GetUserSettingsUseCase(fakeRepository)
    }

    @Test
    fun `should update dark mode setting to true`() = runTest {
        // Act
        updateDarkModeUseCase(true)

        // Assert updated state using getUserSettingsUseCase
        val updated = getUserSettingsUseCase().first()
        assertEquals(true, updated.darkMode)
    }

    @Test
    fun `dark mode should update and flow should emit new state`() = runTest {
        val flow = getUserSettingsUseCase()

        flow.test {
            // Initial default value
            val initial = awaitItem()
            assertEquals(false, initial.darkMode)

            // Update value
            updateDarkModeUseCase(true)

            // Await new emission
            val updated = awaitItem()
            assertEquals(true, updated.darkMode)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
