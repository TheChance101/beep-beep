package di

import data.gateway.FakeRemoteGateWay
import data.gateway.local.LocalConfigurationGateway
import data.gateway.remote.CuisineRemoteGateway
import data.gateway.remote.MealRemoteGateway
import data.gateway.remote.OrderRemoteGateway
import domain.gateway.IFakeRemoteGateway
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.remote.ICuisineRemoteGateway
import domain.gateway.remote.IMealRemoteGateway
import domain.gateway.remote.IOrderRemoteGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
    singleOf(::FakeRemoteGateWay) { bind<IFakeRemoteGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
    singleOf(::CuisineRemoteGateway) { bind<ICuisineRemoteGateway>() }
    singleOf(::MealRemoteGateway) { bind<IMealRemoteGateway>() }
    singleOf(::OrderRemoteGateway) { bind<IOrderRemoteGateway>() }

}