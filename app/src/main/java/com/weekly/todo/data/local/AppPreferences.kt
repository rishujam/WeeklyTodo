package com.weekly.todo.data.local

//class AppPreferences @Inject constructor (
//    private val context: Context
//) {
//
//    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_pref")
//
//    companion object {
//        private val KEY_LAST_OPENED = longPreferencesKey("last_opened")
//    }
//
//
//    suspend fun saveLastOpened(value: Long) {
//        context.dataStore.edit { store ->
//            store[KEY_LAST_OPENED] = value
//        }
//    }
//
//    suspend fun getLastOpened(): Long {
//        return context.dataStore.data.first()[KEY_LAST_OPENED] ?: 0
//    }
//
//}