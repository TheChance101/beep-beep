package di

import data.gateway.fake.FakeOrderRemoteGateway
import data.gateway.fake.FakeRemoteGateway
import data.gateway.local.LocalConfigurationGateway
import data.gateway.remote.RestaurantGateway
import data.gateway.remote.UserRemoteRemoteGateway
import domain.gateway.IFakeRemoteGateway
import domain.gateway.IOrderRemoteGateway
import domain.gateway.IRestaurantRemoteGateway
import domain.gateway.IUserRemoteGateway
import domain.gateway.local.ILocalConfigurationGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gatewayModule = module {
    singleOf(::UserRemoteRemoteGateway) { bind<IUserRemoteGateway>() }
    singleOf(::FakeRemoteGateway) { bind<IFakeRemoteGateway>() }
    singleOf(::RestaurantGateway) { bind<IRestaurantRemoteGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
    singleOf(::FakeOrderRemoteGateway) { bind<IOrderRemoteGateway>() }
}