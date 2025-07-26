package com.android4you.datastorecleanarchitecture.domain.usecases

import com.android4you.datastorecleanarchitecture.data.repository.FakeSettingsRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserSettingsUseCaseTest {

    private val repository = FakeSettingsRepository()
    private val useCase = GetUserSettingsUseCase(repository)

    @Test
    fun returns_correct_initial_data(): Unit = runTest {
        val result = useCase().first()
        assertEquals("en", result.language)
        assertFalse(result.darkMode)
    }
}
