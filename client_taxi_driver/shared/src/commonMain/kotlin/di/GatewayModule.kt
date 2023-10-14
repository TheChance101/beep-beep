package di

import data.local.gateway.LocalConfigurationGateway
import data.remote.fakegateway.MapFakeGateway
import data.remote.gateway.IdentityRemoteGateway
import domain.gateway.IMapGateway
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.remote.IIdentityRemoteGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
    //singleOf(::IdentityFakeGateway) { bind<IIdentityRemoteGateway>() }
    singleOf(::IdentityRemoteGateway) { bind<IIdentityRemoteGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
    singleOf(::MapFakeGateway) { bind<IMapGateway>() }
}