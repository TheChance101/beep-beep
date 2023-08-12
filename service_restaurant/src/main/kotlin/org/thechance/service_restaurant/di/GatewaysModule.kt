package org.thechance.service_restaurant.di

import org.koin.dsl.module
import org.thechance.service_restaurant.data.gateway.RestaurantGateway
import org.thechance.service_restaurant.data.gateway.RestaurantOptionsGateway

val GatewaysModule = module {
    single { RestaurantGateway(get()) }
    single { RestaurantOptionsGateway(get()) }
}