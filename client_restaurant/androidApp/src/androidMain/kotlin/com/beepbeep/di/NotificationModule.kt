package com.beepbeep.di

import com.beepbeep.notification.FirebaseMessagingServiceImp
import com.beepbeep.notification.fcm.FcmNotificationImp
import com.beepbeep.notification.fcm.IFcmNotification
import data.service.IFirebaseMessagingService
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val firebaseModule = module {
    singleOf(::FirebaseMessagingServiceImp) { bind<IFirebaseMessagingService>() }
    single<IFcmNotification> { FcmNotificationImp(androidContext().applicationContext) }
}