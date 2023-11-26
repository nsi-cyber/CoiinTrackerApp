package com.nsicyber.coiintrackerapp.service


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.nsicyber.coiintrackerapp.R
import com.nsicyber.coiintrackerapp.data.db.HawkUtils
import com.nsicyber.coiintrackerapp.data.repository.FirebaseRepository
import com.nsicyber.coiintrackerapp.data.repository.TrackerRepository
import com.nsicyber.coiintrackerapp.model.CoinModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BackgroundTrackerService : Service() {

    private val handler = Handler()

    @Inject
    lateinit var trackerRepository: TrackerRepository

    @Inject
    lateinit var firebaseRepository: FirebaseRepository

    @Suppress("DEPRECATION")
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
        return channelId
    }

    private fun createNotification(): Notification {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "Coiin Tracker")
            } else {
                ""
            }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Coiin Tracker")
            .setContentText("Service working...")
            .setSmallIcon(R.drawable.ic_logo) // Kendi ikonunuzu kullanın
            .setPriority(NotificationCompat.PRIORITY_LOW)

        return notificationBuilder.build()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Servis oluşturuldu.")


        val notification = createNotification()
        startForeground(1, notification)



        if (HawkUtils.getLastUser() != null)


            GlobalScope.launch(Dispatchers.IO) {
                val myCoinsResult =
                    firebaseRepository.readDataFromFirestore(HawkUtils.getLastUser())

                myCoinsResult?.forEach { model ->
                    model.toObject(CoinModel::class.java)?.reloadPerHour?.let { value ->
                        startBackgroundTask(model.toObject(CoinModel::class.java))
                    }

                }


            }

    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


    private fun startBackgroundTask(model: CoinModel?) {
        handler.postDelayed(object : Runnable {
            override fun run() {
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        var notificationService = NotificationService(applicationContext)
                        var responseModel = trackerRepository.getCoinDetail(model?.id!!)
                        firebaseRepository.updateDataInFirestore(HawkUtils.getLastUser(),model?.id,responseModel!!)
                        if (responseModel?.market_data?.current_price != model?.market_data?.current_price) {
                            if ((responseModel?.market_data?.current_price?.usd
                                    ?: 0).toDouble() > (model?.market_data?.current_price?.usd
                                    ?: 0).toDouble()
                            )
                                notificationService.showNotification(model=responseModel,isUp = true)
                            else
                                notificationService.showNotification(model=responseModel,isUp = false)

                        }


                    } catch (e: Exception) {
                        Log.e(TAG, "API isteği başarısız oldu:startBackgroundTask ${e.message}")
                    }
                }
//call after x hour
                handler.postDelayed(this, (model?.reloadPerHour?.toLong()?.times(3600000) ?: 3600000) )
            }

        }, 0)
    }

}
