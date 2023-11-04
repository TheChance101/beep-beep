package com.beepbeep.notification

object ServiceLocator {
    private var fcmNotification: IFCMNotification? = null

    fun initialize(notificationListener: IFCMNotification) {
        this.fcmNotification = notificationListener
    }

    fun getFCMNotification(): IFCMNotification? {
        return fcmNotification
    }
}