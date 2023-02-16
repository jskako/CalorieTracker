package com.jskako.multimodulecalorietracker.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.jskako.core.data.preferences.DefaultPreferences
import com.jskako.core.domain.uce_case.FilterOutDigits
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(
        app: Application
    ): DataStore<Preferences> {
        return app.dataStore
    }

    @Provides
    @Singleton
    fun providePreferences(dataStore: DataStore<Preferences>): com.jskako.core.domain.preferences.AppPreferences {
        return DefaultPreferences(dataStore)
    }

    @Provides
    @Singleton
    fun provideFilterOutDigitUseCase(): FilterOutDigits {
        return FilterOutDigits()
    }
}