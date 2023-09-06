package org.thechance.service_location.di

import org.koin.dsl.module
import org.thechance.service_location.util.SocketHandler

val appModule = module {
    single<SocketHandler> { SocketHandler() }
}