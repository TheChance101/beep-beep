package di

import data.gateway.FakeRemoteGateWay
import data.gateway.local.LocalConfigurationGateway
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.IFakeRemoteGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
    singleOf(::FakeRemoteGateWay) { bind<IFakeRemoteGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
}