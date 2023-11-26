package com.nsicyber.coiintrackerapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseApp
import com.nsicyber.coiintrackerapp.service.BackgroundTrackerService
import com.nsicyber.coiintrackerapp.service.NotificationService
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
        createNotificationChannel()
        val serviceIntent = Intent(this, BackgroundTrackerService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(this, serviceIntent)
        } else {
            this.startService(serviceIntent)
        }
    }



    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationService.TRACKER_CHANNEL,
                "Tracker",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Used for the notifications"

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}