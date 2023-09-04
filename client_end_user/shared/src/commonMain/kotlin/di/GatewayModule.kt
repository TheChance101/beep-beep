package di

import data.gateway.remote.UserGateway
import data.gateway.remote.FakeRemoteGateway
import domain.gateway.IFakeRemoteGateway
import data.gateway.remote.RestaurantGateway
import domain.gateway.IRestaurantRemoteGateway
import domain.gateway.IUserGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gatewayModule = module {
    singleOf(::UserGateway) { bind<IUserGateway>() }
    singleOf(::FakeRemoteGateway) { bind<IFakeRemoteGateway>() }
    singleOf(::RestaurantGateway) { bind<IRestaurantRemoteGateway>() }
}