package com.beepbeep.notification

import android.app.PendingIntent

interface IFCMNotification {
    fun getClickPendingIntent(): PendingIntent
}