package com.beepbeep.notification.fcm

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.beepbeep.MainActivity
import presentation.notification.NotificationScreen

class FCMNotificationImp(private val context: Context) : IFCMNotification {
    override fun getClickPendingIntent(): PendingIntent {
        val clickIntent =
            Intent(
                Intent.ACTION_VIEW,
                USER_NOTIFICATION_URI.toUri(),
                context,
                MainActivity::class.java
            )
        val clickPendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(clickIntent)
            getPendingIntent(1, flag)
        }
        return clickPendingIntent
    }

    companion object {
        const val USER_NOTIFICATION_URI = "https://beep-beep-api-gateway-nap2u.ondigitalocean.app/"
//        const val USER_NOTIFICATION_URI = "http://192.168.1.17:8081/"
        private const val flag = PendingIntent.FLAG_IMMUTABLE
    }
}


