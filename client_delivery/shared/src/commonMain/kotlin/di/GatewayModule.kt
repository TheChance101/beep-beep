package di

import data.gateway.local.LocalConfigurationGateway
import data.gateway.local.LocationGateway
import data.gateway.remote.IdentityRemoteGateway
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.local.ILocationGateway
import domain.gateway.remote.IIdentityRemoteGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gatewayModule = module {
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
    singleOf(::IdentityRemoteGateway) { bind<IIdentityRemoteGateway>() }
    singleOf(::LocationGateway) { bind<ILocationGateway>() }
}