package org.thechance.service_location.di

import org.koin.dsl.module
import org.thechance.service_location.data.SocketHandler

val appModule = module {
    single<SocketHandler> { SocketHandler() }
}