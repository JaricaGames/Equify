package com.jarica.compartirgastos.core.di

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.jarica.compartirgastos.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Clave del parámetro de Remote Config que define el versionCode mínimo permitido. */
const val MIN_VERSION_CODE_KEY = "min_version_code"

@Module
@InstallIn(SingletonComponent::class)
object RemoteConfigModule {

    @Provides
    @Singleton
    fun provideRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig = Firebase.remoteConfig
        val settings = remoteConfigSettings {
            // En debug refrescamos en cada arranque; en release cada hora.
            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) 0 else 3600
        }
        remoteConfig.setConfigSettingsAsync(settings)
        // Por defecto, el mínimo es la versión actual: nunca bloquea hasta que lo subas en consola.
        remoteConfig.setDefaultsAsync(mapOf(MIN_VERSION_CODE_KEY to BuildConfig.VERSION_CODE.toLong()))
        return remoteConfig
    }
}
