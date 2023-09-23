package di

import data.remote.gateway.IdentityRemoteGateway
import data.remote.fakegateway.IdentityFakeGateway
import domain.gateway.remote.IIdentityRemoteGateway
import domain.gateway.local.ILocalConfigurationGateway
import data.local.gateway.LocalConfigurationGateway
import data.remote.fakegateway.MapFakeGateway
import domain.gateway.IMapGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
//    singleOf(::IdentityRemoteGateway) { bind<IIdentityRemoteGateway>() }
    singleOf(::IdentityFakeGateway) { bind<IIdentityRemoteGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
    singleOf(::MapFakeGateway) { bind<IMapGateway>() }
}