package com.android4you.datastorecleanarchitecture.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
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
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val USER_SETTINGS_KEY = "user_settings"

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                File(context.filesDir, "datastore/$USER_SETTINGS_KEY.preferences_pb")
            }
        )
    }
    @Provides
    @Singleton
    fun provideSettingsRepository(
        dataStore: DataStore<Preferences>
    ): ISettingsRepository = SettingsRepositoryImpl(dataStore)

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
