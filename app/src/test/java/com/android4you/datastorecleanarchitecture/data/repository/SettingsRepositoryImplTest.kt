package com.android4you.datastorecleanarchitecture.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.Closeable
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsRepositoryImplTest {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var repository: SettingsRepositoryImpl

    private val testScope = TestScope(UnconfinedTestDispatcher())

    @Before
    fun setup() {
        val tempFile = File.createTempFile("test_prefs", ".preferences_pb")
        dataStore = PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { tempFile }
        )

        repository = SettingsRepositoryImpl(dataStore)
    }

    // Update All Values and Verify Together
    @Test
    fun testUpdateAndReadUserSettings() = testScope.runTest {
        // Arrange
        repository.updateDarkMode(true)
        repository.updateLanguage("fr")
        repository.updateUsername("Manu")

        // Act
        val result = repository.getUserSettings().first()

        // Assert
        assertEquals(true, result.darkMode)
        assertEquals("fr", result.language)
        assertEquals("Manu", result.username)
    }

    @Test
    fun updateDarkMode_updatesAndReadsCorrectly() = runTest {
        repository.updateDarkMode(true)

        val settings = repository.getUserSettings().first()

        assertTrue(settings.darkMode)
    }

    @Test
    fun updateLanguage_updatesAndReadsCorrectly() = runTest {
        repository.updateLanguage("fr")

        val settings = repository.getUserSettings().first()

        assertEquals("fr", settings.language)
    }

    @Test
    fun updateUsername_updatesAndReadsCorrectly() = runTest {
        repository.updateUsername("JohnDoe")

        val settings = repository.getUserSettings().first()

        assertEquals("JohnDoe", settings.username)
    }

    // Default Values When Nothing is Set
    @Test
    fun defaultValues_areReturnedWhenNoPreferencesSet() = runTest {
        val settings = repository.getUserSettings().first()

        assertFalse(settings.darkMode)
        assertEquals("en", settings.language)
        assertEquals("", settings.username)
    }

    // Multiple Updates Should Overwrite Value
    @Test
    fun multipleUpdates_overwritePreviousValues() = runTest {
        repository.updateLanguage("es")
        repository.updateLanguage("de")

        val settings = repository.getUserSettings().first()

        assertEquals("de", settings.language)
    }

    // Concurrent Updates Do Not Corrupt Data
    // Simulate multiple edit calls in quick succession.
    @Test
    fun concurrentUpdates_doNotConflict() = runTest {
        // Launch two concurrent updates
        val job1 = launch { repository.updateUsername("User1") }
        val job2 = launch { repository.updateUsername("User2") }

        // Wait for both jobs to complete
        job1.join()
        job2.join()

        // Now safely read the value
        val settings = repository.getUserSettings().first()

        // One of them should win, both are valid outcomes
        assertTrue(
            settings.username == "User1" || settings.username == "User2"
        )
    }


    // Stress Test – Multiple Writes and Reads
    @Test
    fun stressTest_multipleWritesAndReads() = runTest {
        repeat(100) {
            repository.updateUsername("User$it")
        }

        val settings = repository.getUserSettings().first()
        assertEquals("User99", settings.username)
    }

    // Language and Username Should Not Return Null (Validation)
    @Test
    fun nullSafety_shouldReturnEmptyOrDefault() = runTest {
        val settings = repository.getUserSettings().first()

        assertNotNull(settings.language)
        assertNotNull(settings.username)
    }

    @After
    fun cleanup() {
        (dataStore as? Closeable)?.close()
    }
}
