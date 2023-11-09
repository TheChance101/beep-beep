package com.beepbeep.di

import com.beepbeep.notification.FCMNotificationImp
import com.beepbeep.notification.FireBaseMsgServiceImpl
import com.beepbeep.notification.IFCMNotification
import data.gateway.service.IFireBaseMessageService
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val firebaseModule = module {
//    single { FireBaseMsgServiceImpl() }
//    single<IFireBaseMessageService> { FirebaseMessagingImpl() }

    singleOf(::FireBaseMsgServiceImpl) { bind<IFireBaseMessageService>() }
    single<IFCMNotification> { FCMNotificationImp(androidContext().applicationContext) }
}