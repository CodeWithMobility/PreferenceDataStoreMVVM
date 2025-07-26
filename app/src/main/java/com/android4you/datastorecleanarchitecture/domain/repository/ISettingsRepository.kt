package com.android4you.datastorecleanarchitecture.domain.repository

import com.android4you.datastorecleanarchitecture.domain.model.UserSettings
import kotlinx.coroutines.flow.Flow


interface ISettingsRepository {
    fun getUserSettings(): Flow<UserSettings>
    suspend fun updateDarkMode(enabled: Boolean)
    suspend fun updateLanguage(lang: String)
    suspend fun updateUsername(name: String)
}
