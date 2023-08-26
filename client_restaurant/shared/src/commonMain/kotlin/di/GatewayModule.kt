package di

import data.gateway.FakeRemoteGateway
import data.gateway.LocalConfigurationGateway
import domain.gateway.ILocalConfigurationGateway
import domain.gateway.IRemoteGateWay
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
    singleOf(::FakeRemoteGateway){ bind<IRemoteGateWay>()}
    //singleOf(::RemoteGateway) { bind<IRemoteGateWay>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
}