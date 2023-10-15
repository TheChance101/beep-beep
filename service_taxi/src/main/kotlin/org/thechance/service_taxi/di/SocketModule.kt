package org.thechance.service_taxi.di

import org.koin.dsl.module
import org.thechance.service_taxi.api.util.SocketHandler

val SocketModule = module {
    single<SocketHandler> { SocketHandler() }
}