package di

import data.gateway.local.LocalConfigurationGateway
import data.gateway.remote.FakeRemoteGateway
import data.gateway.remote.RestaurantGateway
import data.gateway.remote.UserGateway
import domain.gateway.IFakeRemoteGateway
import domain.gateway.IRestaurantRemoteGateway
import domain.gateway.IUserGateway
import domain.gateway.local.ILocalConfigurationGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gatewayModule = module {
    singleOf(::FakeRemoteGateway) { bind<IFakeRemoteGateway>() }
    singleOf(::UserGateway) { bind<IUserGateway>() }
    singleOf(::RestaurantGateway) { bind<IRestaurantRemoteGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
}