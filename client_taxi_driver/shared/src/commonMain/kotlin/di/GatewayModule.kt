package di

import data.local.gateway.LocalConfigurationGateway
import data.remote.fakegateway.IdentityFakeGateway
import data.remote.fakegateway.OrderFakeGateway
import data.remote.gateway.LocationRemoteGateway
import domain.gateway.ILocationGateway
import domain.gateway.IOrderGateway
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.remote.IIdentityRemoteGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
//    singleOf(::IdentityRemoteGateway) { bind<IIdentityRemoteGateway>() }
    singleOf(::IdentityFakeGateway) { bind<IIdentityRemoteGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
    singleOf(::OrderFakeGateway) { bind<IOrderGateway>() }
    //    singleOf(::LocationFakeGateway) { bind<ILocationGateway>() }
    singleOf(::LocationRemoteGateway) { bind<ILocationGateway>() }
}