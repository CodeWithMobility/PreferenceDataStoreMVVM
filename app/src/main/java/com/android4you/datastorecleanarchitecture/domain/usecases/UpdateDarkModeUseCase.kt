package com.android4you.datastorecleanarchitecture.domain.usecases

import com.android4you.datastorecleanarchitecture.domain.repository.ISettingsRepository
import javax.inject.Inject

class UpdateDarkModeUseCase @Inject constructor(private val repo: ISettingsRepository) {
    suspend operator fun invoke(enabled: Boolean) = repo.updateDarkMode(enabled)
}
