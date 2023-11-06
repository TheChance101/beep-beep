package com.beepbeep.notification.fcm

import android.app.PendingIntent

interface IFCMNotification {
    fun getClickPendingIntent(): PendingIntent
}