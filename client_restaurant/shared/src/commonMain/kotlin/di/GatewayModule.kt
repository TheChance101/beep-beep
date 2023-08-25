package di

import data.gateway.FakeRemoteGateway
import data.gateway.LocalGateway
import data.gateway.RemoteGateway
import domain.gateway.ILocalGateWay
import domain.gateway.IRemoteGateWay
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
    singleOf(::FakeRemoteGateway){ bind<IRemoteGateWay>()}
    singleOf(::FakeRemoteGateway) { bind<IRemoteGateWay>() }
    singleOf(::RemoteGateway) { bind<IRemoteGateWay>() }
    singleOf(::LocalGateway) { bind<ILocalGateWay>() }
}