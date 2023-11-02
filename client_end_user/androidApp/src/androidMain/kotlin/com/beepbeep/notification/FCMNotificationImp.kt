package com.beepbeep.notification

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.beepbeep.MainActivity

class FCMNotificationImp (private val context: Context) : IFCMNotification {
    private val flag = PendingIntent.FLAG_IMMUTABLE

    companion object{
//        const val USER_NOTIFICATION_URI = "https://beep-beep-notification-cp5hp.ondigitalocean.app/"
        const val USER_NOTIFICATION_URI = "http://192.168.1.17:8086/"
    }

    override fun getClickPendingIntent(): PendingIntent {
        val clickIntent =
            Intent(Intent.ACTION_VIEW, USER_NOTIFICATION_URI.toUri(), context, MainActivity::class.java)
        val clickPendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(clickIntent)
            getPendingIntent(1,flag)
        }
        return clickPendingIntent
    }
}


