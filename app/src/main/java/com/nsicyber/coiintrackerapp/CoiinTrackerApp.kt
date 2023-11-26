package com.nsicyber.coiintrackerapp

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoiinTrackerApp : Application() {

    override fun onCreate() {

        super.onCreate()
        FirebaseApp.initializeApp(this)

    }


}