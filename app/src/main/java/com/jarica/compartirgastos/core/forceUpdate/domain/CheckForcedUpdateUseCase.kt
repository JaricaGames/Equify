package com.jarica.compartirgastos.core.forceUpdate.domain

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.jarica.compartirgastos.BuildConfig
import com.jarica.compartirgastos.core.di.MIN_VERSION_CODE_KEY
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Devuelve true si hay que forzar la actualización, es decir, si el versionCode
 * instalado es menor que el min_version_code definido en Remote Config.
 *
 * Ante cualquier fallo (sin red, fetch fallido) devuelve false: nunca bloqueamos
 * al usuario por un problema ajeno a la versión.
 */
class CheckForcedUpdateUseCase @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) {
    suspend operator fun invoke(): Boolean = try {
        remoteConfig.fetchAndActivate().await()
        val minVersionCode = remoteConfig.getLong(MIN_VERSION_CODE_KEY)
        BuildConfig.VERSION_CODE < minVersionCode
    } catch (e: Exception) {
        false
    }
}
