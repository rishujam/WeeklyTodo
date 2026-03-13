package com.weekly.todo.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AppPreferences @Inject constructor (
    private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_pref")

    companion object {
        private val KEY_USER_UUID = stringPreferencesKey("user_uuid")
    }

    suspend fun saveUUID(value: String) {
        context.dataStore.edit { store ->
            store[KEY_USER_UUID] = value
        }
    }

    suspend fun getUUID(): String? {
        return context.dataStore.data.first()[KEY_USER_UUID]
    }
}