package com.beepbeep.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.thechance.beepbeep.R

class FireBaseMsgServiceImpl() : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("TAG", "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val notification = remoteMessage.notification
        if (notification != null) {
            val title = notification.title
            val body = notification.body
            showNotification(title, body)
        }
    }

    private fun showNotification(title: String?, message: String?) {
        val clickPendingIntent = ServiceLocator.getFCMNotification()?.getClickPendingIntent()
        val channelId = "default_channel"
        val notificationId = 1
        val builder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.ic_beep_beep_logo)
            setContentTitle(title)
            setContentText(message)
            priority = NotificationCompat.PRIORITY_HIGH
            addAction(0, "Show", clickPendingIntent)
        }

        clickPendingIntent?.let {
            builder.setContentIntent(it)
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId, builder.build())
    }

}