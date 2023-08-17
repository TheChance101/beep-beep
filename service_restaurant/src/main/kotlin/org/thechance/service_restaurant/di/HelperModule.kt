package org.thechance.service_restaurant.di

import org.koin.dsl.module

val HelperModule = module {
    single<OrderSocketHandler> { OrderSocketHandler() }
}