package com.jarica.compartirgastos.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jarica.compartirgastos.core.PREFERENCES_NAME
import kotlinx.coroutines.flow.first
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

class Preferences @Inject constructor(
    private val context: Context
) {

   /* suspend fun saveIdGroup(key:String, value:Int){
        val preferenceKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = value

        }
    }

    suspend fun getIdGroup(key: String):Int?{
        return try {
            val preferenceKey = intPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferenceKey]

        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }*/

}