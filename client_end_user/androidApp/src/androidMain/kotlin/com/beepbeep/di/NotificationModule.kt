package com.beepbeep.di

import com.beepbeep.notification.FCMNotificationImp
import com.beepbeep.notification.FireBaseMsgServiceImpl
import com.beepbeep.notification.IFCMNotification
import com.beepbeep.notification.ServiceLocator
import com.google.firebase.messaging.FirebaseMessaging
import data.gateway.service.IFireBaseMessageService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val firebaseMessagingModule = module {
    single<IFireBaseMessageService> {
        FireBaseMsgServiceImpl(
            FirebaseMessaging.getInstance(),
            ServiceLocator.getFCMNotification()?.getClickPendingIntent()
        )
    }
}
val firebaseMessagingImplementationModule = module {
    single { FireBaseMsgServiceImpl(get(), get()) }
}

val fcmNotificationModule = module {
    single<IFCMNotification> { FCMNotificationImp(androidContext().applicationContext) }
}