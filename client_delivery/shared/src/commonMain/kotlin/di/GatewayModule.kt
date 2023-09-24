package di

import data.gateway.local.LocalConfigurationGateway
import data.gateway.remote.FakeRemoteGateway
import data.gateway.remote.IdentityRemoteGateway
import domain.gateway.remote.IFakeRemoteGateway
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.remote.IIdentityRemoteGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gatewayModule = module {
    singleOf(::FakeRemoteGateway) { bind<IFakeRemoteGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
    singleOf(::IdentityRemoteGateway) { bind<IIdentityRemoteGateway>() }
}