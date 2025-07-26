package com.android4you.datastorecleanarchitecture.domain.usecases

import com.android4you.datastorecleanarchitecture.domain.model.UserSettings
import com.android4you.datastorecleanarchitecture.domain.repository.ISettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserSettingsUseCase @Inject constructor(private val repo: ISettingsRepository) {
    operator fun invoke(): Flow<UserSettings> = repo.getUserSettings()
}

