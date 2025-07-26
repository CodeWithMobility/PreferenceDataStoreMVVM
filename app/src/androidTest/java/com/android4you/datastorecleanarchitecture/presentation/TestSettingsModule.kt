package com.android4you.datastorecleanarchitecture.presentation

import android.content.Context
import com.android4you.datastorecleanarchitecture.di.AppModule
import com.android4you.datastorecleanarchitecture.domain.repository.ISettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class] // This is important
)
object TestSettingsModule {

    @Provides
    @Singleton
    fun provideFakeSettingsRepository(): ISettingsRepository = FakeSettingsRepository()
}
