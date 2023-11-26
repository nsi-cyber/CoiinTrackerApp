package com.nsicyber.coiintrackerapp

import android.app.Application
import com.google.firebase.FirebaseApp
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoiinTrackerApp : Application() {

    override fun onCreate() {

        super.onCreate()
        FirebaseApp.initializeApp(this)
        if (!Hawk.isBuilt()) {
            Hawk.init(this).build()
        }
    }


}