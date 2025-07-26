package com.android4you.datastorecleanarchitecture.domain.usecases

import com.android4you.datastorecleanarchitecture.domain.repository.ISettingsRepository
import javax.inject.Inject

class UpdateUsernameUseCase @Inject constructor (private val repo: ISettingsRepository) {
    suspend operator fun invoke(name: String) = repo.updateUsername(name)
}