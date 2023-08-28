package di

import data.gateway.remote.FakeRemoteGateWay
import data.gateway.local.LocalConfigurationGateway
import domain.gateway.ILocalConfigurationGateway
import domain.gateway.IRemoteGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
    singleOf(::FakeRemoteGateWay) { bind<IRemoteGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
}