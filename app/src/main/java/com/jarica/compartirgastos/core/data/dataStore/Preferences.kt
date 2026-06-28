package com.jarica.compartirgastos.core.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jarica.compartirgastos.core.utils.PREFERENCES_NAME
import kotlinx.coroutines.flow.first
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

class Preferences @Inject constructor(
    private val context: Context
) {

    suspend fun saveIdGroup(key:String, value:Int){
        val preferenceKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = value

        }
    }

    suspend fun getIdGroup(key: String): String?{
        return try {
            val preferenceKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferenceKey]

        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun incrementAndGetLaunchCount(): Int {
        val key = intPreferencesKey("app_launch_count")
        var count = 0
        context.dataStore.edit { prefs ->
            count = (prefs[key] ?: 0) + 1
            prefs[key] = count
        }
        return count
    }

    suspend fun isReviewRequested(): Boolean {
        val key = booleanPreferencesKey("review_requested")
        return context.dataStore.data.first()[key] ?: false
    }

    suspend fun setReviewRequested() {
        val key = booleanPreferencesKey("review_requested")
        context.dataStore.edit { it[key] = true }
    }

    /**
     * Cache local del estado de la compra "quitar anuncios". Es solo una caché para
     * que la UI no parpadee al arrancar: la fuente de verdad es Google Play
     * (BillingManager refresca este valor al conectarse).
     */
    suspend fun isAdsRemoved(): Boolean {
        val key = booleanPreferencesKey("ads_removed")
        return context.dataStore.data.first()[key] ?: false
    }

    suspend fun setAdsRemoved(value: Boolean) {
        val key = booleanPreferencesKey("ads_removed")
        context.dataStore.edit { it[key] = value }
    }

}