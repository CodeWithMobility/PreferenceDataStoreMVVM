package com.android4you.datastorecleanarchitecture.domain.usecases

import com.android4you.datastorecleanarchitecture.domain.repository.ISettingsRepository
import javax.inject.Inject


class UpdateLanguageUseCase @Inject constructor (private val repo: ISettingsRepository) {
    suspend operator fun invoke(lang: String) = repo.updateLanguage(lang)
}
