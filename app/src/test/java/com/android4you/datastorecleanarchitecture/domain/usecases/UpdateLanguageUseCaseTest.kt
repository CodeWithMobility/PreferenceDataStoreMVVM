package com.android4you.datastorecleanarchitecture.domain.usecases

import com.android4you.datastorecleanarchitecture.data.repository.FakeSettingsRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateLanguageUseCaseTest {

    private lateinit var fakeRepository: FakeSettingsRepository
    private lateinit var updateLanguageUseCase: UpdateLanguageUseCase
    private lateinit var getUserSettingsUseCase: GetUserSettingsUseCase

    @Before
    fun setup() {
        fakeRepository = FakeSettingsRepository()
        updateLanguageUseCase = UpdateLanguageUseCase(fakeRepository)
        getUserSettingsUseCase = GetUserSettingsUseCase(fakeRepository)
    }

    @Test
    fun `should update language setting to fr`() = runTest {
        // Act
        updateLanguageUseCase("fr")

        // Assert updated state using getUserSettingsUseCase
        val updated = getUserSettingsUseCase().first()
        assertEquals("fr", updated.language)
    }
}