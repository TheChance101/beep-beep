package di

import data.remote.gateway.FakeRemoteGateWay
import data.local.gateway.LocalGateWay
import data.remote.gateway.RemoteGateWay
import domain.gateway.ILocalGateWay
import domain.gateway.IRemoteGateWay
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
    singleOf(::FakeRemoteGateWay){ bind<IRemoteGateWay>()}
    singleOf(::RemoteGateWay) { bind<IRemoteGateWay>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
}