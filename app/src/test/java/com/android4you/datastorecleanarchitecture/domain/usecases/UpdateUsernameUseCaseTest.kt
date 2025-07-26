package com.android4you.datastorecleanarchitecture.domain.usecases

import com.android4you.datastorecleanarchitecture.data.repository.FakeSettingsRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UpdateUsernameUseCaseTest {

    private lateinit var fakeRepository: FakeSettingsRepository
    private lateinit var updateUsernameUseCase: UpdateUsernameUseCase
    private lateinit var getUserSettingsUseCase: GetUserSettingsUseCase

    @Before
    fun setup() {
        fakeRepository = FakeSettingsRepository()
        updateUsernameUseCase = UpdateUsernameUseCase(fakeRepository)
        getUserSettingsUseCase = GetUserSettingsUseCase(fakeRepository)
    }

    @Test
    fun `should update language setting to fr`() = runTest {
        // Act
        updateUsernameUseCase("John")

        // Assert updated state using getUserSettingsUseCase
        val updated = getUserSettingsUseCase().first()
        assertEquals("John", updated.username)
    }
}