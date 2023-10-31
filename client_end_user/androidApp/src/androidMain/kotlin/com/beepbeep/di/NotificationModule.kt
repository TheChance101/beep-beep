package com.beepbeep.di

import com.beepbeep.notification.FCMNotificationImp
import com.beepbeep.notification.FireBaseMsgServiceImpl
import com.beepbeep.notification.IFCMNotification
import data.gateway.service.IFireBaseMessageService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val firebaseMessagingModule = module {
    single<IFireBaseMessageService> { FireBaseMsgServiceImpl() }
//    single<IFireBaseMessageService> {
//        FireBaseMsgServiceImpl(
//            FirebaseMessaging.getInstance(),
//            ServiceLocator.getFCMNotification()?.getClickPendingIntent()
//        )
//    }
}

val fcmNotificationModule = module {
    single<IFCMNotification> { FCMNotificationImp(androidContext().applicationContext) }
}