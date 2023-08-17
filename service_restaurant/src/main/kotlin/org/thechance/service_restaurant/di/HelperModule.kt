package org.thechance.service_restaurant.di

import org.koin.dsl.module
import org.thechance.service_restaurant.api.utils.OrderSocketHandler

val HelperModule = module {
    single<OrderSocketHandler> { OrderSocketHandler() }
}