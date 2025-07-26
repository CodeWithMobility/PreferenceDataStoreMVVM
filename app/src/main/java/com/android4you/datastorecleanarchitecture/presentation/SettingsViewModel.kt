package com.android4you.datastorecleanarchitecture.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android4you.datastorecleanarchitecture.domain.model.UserSettings
import com.android4you.datastorecleanarchitecture.domain.usecases.GetUserSettingsUseCase
import com.android4you.datastorecleanarchitecture.domain.usecases.UpdateDarkModeUseCase
import com.android4you.datastorecleanarchitecture.domain.usecases.UpdateLanguageUseCase
import com.android4you.datastorecleanarchitecture.domain.usecases.UpdateUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getSettings: GetUserSettingsUseCase,
    private val updateDarkMode: UpdateDarkModeUseCase,
    private val updateLanguage: UpdateLanguageUseCase,
    private val updateUsername: UpdateUsernameUseCase
) : ViewModel() {

    val settings = getSettings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UserSettings(false, "en", ""))

    fun onDarkModeChange(enabled: Boolean) {
        viewModelScope.launch { updateDarkMode(enabled) }
    }

    fun onLanguageChange(lang: String) {
        viewModelScope.launch { updateLanguage(lang) }
    }

    fun onUsernameChange(name: String) {
        viewModelScope.launch { updateUsername(name) }
    }
}
