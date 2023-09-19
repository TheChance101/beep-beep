package di

import data.remote.fakegateway.LocationFakeGateway
import data.remote.fakegateway.OrderFakeGateway
import domain.gateway.ILocationGateway
import domain.gateway.IOrderGateway
import data.remote.gateway.IdentityRemoteGateway
import data.remote.fakegateway.IdentityFakeGateway
import domain.gateway.remote.IIdentityRemoteGateway
import domain.gateway.local.ILocalConfigurationGateway
import data.local.gateway.LocalConfigurationGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
//    singleOf(::IdentityRemoteGateway) { bind<IIdentityRemoteGateway>() }
    singleOf(::IdentityFakeGateway) { bind<IIdentityRemoteGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
    singleOf(::OrderFakeGateway) { bind<IOrderGateway>() }
    singleOf(::LocationFakeGateway) { bind<ILocationGateway>() }
}