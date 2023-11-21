package com.beepbeep.notification.fcm

import android.app.PendingIntent

interface IFcmNotification {
    fun getClickPendingIntent(): PendingIntent
}