package com.android4you.datastorecleanarchitecture.di

import android.content.Context
import com.android4you.datastorecleanarchitecture.data.repository.SettingsRepositoryImpl
import com.android4you.datastorecleanarchitecture.domain.repository.ISettingsRepository
import com.android4you.datastorecleanarchitecture.domain.usecases.GetUserSettingsUseCase
import com.android4you.datastorecleanarchitecture.domain.usecases.UpdateDarkModeUseCase
import com.android4you.datastorecleanarchitecture.domain.usecases.UpdateLanguageUseCase
import com.android4you.datastorecleanarchitecture.domain.usecases.UpdateUsernameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(
        @ApplicationContext context: Context
    ): ISettingsRepository = SettingsRepositoryImpl(context)

    @Provides
    fun provideGetUserSettingsUseCase(
        repo: ISettingsRepository
    ): GetUserSettingsUseCase = GetUserSettingsUseCase(repo)

    @Provides
    fun provideUpdateDarkModeUseCase(
        repo: ISettingsRepository
    ): UpdateDarkModeUseCase = UpdateDarkModeUseCase(repo)

    @Provides
    fun provideUpdateLanguageUseCase(
        repo: ISettingsRepository
    ): UpdateLanguageUseCase = UpdateLanguageUseCase(repo)

    @Provides
    fun provideUpdateUsernameUseCase(
        repo: ISettingsRepository
    ): UpdateUsernameUseCase = UpdateUsernameUseCase(repo)
}
