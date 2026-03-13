package com.weekly.todo

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.weekly.todo.data.local.AppPreferences
import com.weekly.todo.data.local.InMemoryData
import com.weekly.todo.util.Constants
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltAndroidApp
class WeeklyApp : Application() {

    @Inject
    lateinit var appPreferences: AppPreferences
    
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()
        firebaseAnalytics = Firebase.analytics
        CoroutineScope(Dispatchers.IO).launch {
            val userUUID = getUserUUID()
            InMemoryData.userUUID = userUUID
            firebaseAnalytics.setUserProperty(Constants.Analytics.USER_ID, InMemoryData.userUUID)
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)
        }
    }
    
    private suspend fun getUserUUID(): String {
        var uuid = appPreferences.getUUID()
        if (uuid.isNullOrEmpty()) {
            uuid = UUID.randomUUID().toString()
            appPreferences.saveUUID(uuid)
        }
        return uuid
    }

}