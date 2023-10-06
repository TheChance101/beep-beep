package di

import data.gateway.local.LocalConfigurationGateway
import data.gateway.remote.CuisineRemoteGateway
import data.gateway.remote.IdentityRemoteGateway
import data.gateway.remote.MealRemoteGateway
import data.gateway.remote.OrderRemoteGateway
import data.gateway.remote.RestaurantRemoteGateWay
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.remote.ICuisineRemoteGateway
import domain.gateway.remote.IIdentityRemoteGateway
import domain.gateway.remote.IMealRemoteGateway
import domain.gateway.remote.IOrderRemoteGateway
import domain.gateway.remote.IRestaurantRemoteGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
//    singleOf(::FakeRemoteGateWay) { bind<IFakeRemoteGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
    singleOf(::CuisineRemoteGateway) { bind<ICuisineRemoteGateway>() }
    singleOf(::MealRemoteGateway) { bind<IMealRemoteGateway>() }
    singleOf(::IdentityRemoteGateway) { bind<IIdentityRemoteGateway>() }
    singleOf(::OrderRemoteGateway) { bind<IOrderRemoteGateway>() }
    singleOf(::RestaurantRemoteGateWay) { bind<IRestaurantRemoteGateway>() }

}