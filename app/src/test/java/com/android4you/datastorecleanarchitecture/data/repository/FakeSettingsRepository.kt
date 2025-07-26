package com.android4you.datastorecleanarchitecture.data.repository

import com.android4you.datastorecleanarchitecture.domain.model.UserSettings
import com.android4you.datastorecleanarchitecture.domain.repository.ISettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSettingsRepository : ISettingsRepository {

    private val _flow = MutableStateFlow(
        UserSettings(darkMode = false, language = "en", username = "Guest")
    )

    override fun getUserSettings(): Flow<UserSettings> = _flow

    override suspend fun updateDarkMode(enabled: Boolean) {
        _flow.value = _flow.value.copy(darkMode = enabled)
    }

    override suspend fun updateLanguage(lang: String) {
        _flow.value = _flow.value.copy(language = lang)
    }

    override suspend fun updateUsername(name: String) {
        _flow.value = _flow.value.copy(username = name)
    }
}
