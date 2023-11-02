package com.beepbeep.di

import com.beepbeep.notification.FCMNotificationImp
import com.beepbeep.notification.FireBaseMsgServiceImpl
import com.beepbeep.notification.FirebaseMessagingImpl
import com.beepbeep.notification.IFCMNotification
import data.gateway.service.IFireBaseMessageService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val firebaseModule = module {
    single { FireBaseMsgServiceImpl() }
    single<IFireBaseMessageService> { FirebaseMessagingImpl() }
    single<IFCMNotification> { FCMNotificationImp(androidContext().applicationContext) }
}