package org.thechance.service_restaurant.di

import org.koin.dsl.module
import org.thechance.service_restaurant.api.utils.SocketHandler

val HelperModule = module {
    single<SocketHandler> { SocketHandler() }
}