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
    private val runnableMap = mutableMapOf<String, Runnable>()

    @Inject
    lateinit var trackerRepository: TrackerRepository

    @Inject
    lateinit var firebaseRepository: FirebaseRepository

    companion object {
        private const val TAG = "BackgroundTrackerService"
        private const val NOTIFICATION_CHANNEL_ID = "my_service"
        private const val NOTIFICATION_CHANNEL_NAME = "Coiin Tracker"
        private const val NOTIFICATION_ID = 1
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Tracker service created.")

        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)

        if (HawkUtils.getLastUser() != null) {
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
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun addRunnable(id: String, model: CoinModel) {
        val runnable = object : Runnable {
            override fun run() {
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val notificationService = NotificationService(applicationContext)
                        val responseModel = trackerRepository.getCoinDetail(model.id!!)
                        firebaseRepository.updateDataInFirestore(
                            HawkUtils.getLastUser(),
                            model.id,
                            responseModel?.copy(isLikedByUser = true,reloadPerHour = model.reloadPerHour)!!
                        )

                        if (responseModel.market_data?.current_price != model.market_data?.current_price) {
                            if ((responseModel.market_data?.current_price?.usd
                                    ?: 0).toDouble() > (model.market_data?.current_price?.usd
                                    ?: 0).toDouble()
                            )
                                notificationService.showNotification(
                                    model = responseModel,
                                    isUp = true
                                )
                            else
                                notificationService.showNotification(
                                    model = responseModel,
                                    isUp = false
                                )
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "API call failed: addRunnable ${e.message}")
                    }
                }
                handler.postDelayed(
                    this,
                    (model.reloadPerHour?.toLong()?.times(3600000) ?: 3600000)
                )
            }
        }

        handler.postDelayed(runnable, 0)
        runnableMap[id] = runnable
    }

    fun cancelRunnable(id: String) {
        runnableMap[id]?.let {
            handler.removeCallbacks(it)
            runnableMap.remove(id)
        }
    }

    private fun startBackgroundTask(model: CoinModel?) {
        model?.id?.let { addRunnable(it, model) }
    }

    private fun createNotification(): Notification {
        createNotificationChannel()

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Coiin Tracker")
            .setContentText("Service working...")
            .setSmallIcon(R.drawable.ic_logo)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        return notificationBuilder.build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}

