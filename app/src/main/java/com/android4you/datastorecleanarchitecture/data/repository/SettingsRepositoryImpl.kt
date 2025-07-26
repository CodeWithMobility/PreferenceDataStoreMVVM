package com.android4you.datastorecleanarchitecture.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.android4you.datastorecleanarchitecture.domain.model.UserSettings
import com.android4you.datastorecleanarchitecture.domain.repository.ISettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val DARK_MODE_PREF_KEY = "dark_mode"
private const val LANGUAGE_PREF_KEY = "language"
private const val USERNAME_PREF_KEY = "username"

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ISettingsRepository {

    //private val Context.dataStore by preferencesDataStore(name = USER_SETTINGS_KEY)

    companion object {
        val DARK_MODE_KEY = booleanPreferencesKey(DARK_MODE_PREF_KEY)
        val LANGUAGE_KEY = stringPreferencesKey(LANGUAGE_PREF_KEY)
        val USERNAME_KEY = stringPreferencesKey(USERNAME_PREF_KEY)
    }

    override fun getUserSettings(): Flow<UserSettings> =
        dataStore.data.map { prefs ->
            UserSettings(
                darkMode = prefs[DARK_MODE_KEY] ?: false,
                language = prefs[LANGUAGE_KEY] ?: "en",
                username = prefs[USERNAME_KEY] ?: ""
            )
        }

    override suspend fun updateDarkMode(enabled: Boolean) {
        dataStore.edit { it[DARK_MODE_KEY] = enabled }
    }

    override suspend fun updateLanguage(lang: String) {
       dataStore.edit { it[LANGUAGE_KEY] = lang }
    }

    override suspend fun updateUsername(name: String) {
        dataStore.edit { it[USERNAME_KEY] = name }
    }
}
