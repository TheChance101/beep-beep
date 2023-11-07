package di

import data.local.gateway.LocalConfigurationGateway
import data.remote.fakegateway.OrderFakeGateway
import data.remote.gateway.IdentityRemoteGateway
import data.remote.gateway.LocationRemoteGateway
import domain.gateway.ILocationGateway
import domain.gateway.IOrderGateway
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.remote.IIdentityRemoteGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
    //singleOf(::IdentityFakeGateway) { bind<IIdentityRemoteGateway>() }
    //singleOf(::LocationFakeGateway) { bind<ILocationGateway>() }
    singleOf(::IdentityRemoteGateway) { bind<IIdentityRemoteGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
    singleOf(::OrderFakeGateway) { bind<IOrderGateway>() }
    singleOf(::LocationRemoteGateway) { bind<ILocationGateway>() }
}