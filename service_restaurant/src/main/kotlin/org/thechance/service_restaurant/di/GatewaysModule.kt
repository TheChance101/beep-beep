package org.thechance.service_restaurant.di

import org.koin.dsl.module
import org.thechance.service_restaurant.data.gateway.RestaurantGateway
import org.thechance.service_restaurant.data.gateway.RestaurantOptionsGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway

val GatewaysModule = module {
    single<IRestaurantGateway> { RestaurantGateway(get()) }
    single<IRestaurantOptionsGateway> { RestaurantOptionsGateway(get()) }
}