package di

import data.gateway.FakeRemoteGateway
import data.gateway.LocalConfigurationGateway
import domain.gateway.ILocalConfigurationGateway
import domain.gateway.IFakeRemoteGateWay
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
    //todo add remaining gateway
    singleOf(::FakeRemoteGateway){ bind<IFakeRemoteGateWay>()}
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
}