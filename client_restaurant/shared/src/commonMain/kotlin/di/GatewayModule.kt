package di

import data.gateway.FakeRemoteGateWay
import data.gateway.local.LocalConfigurationGateway
import data.gateway.remote.RemoteCuisineGateway
import data.gateway.remote.RemoteMealGateway
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.IFakeRemoteGateway
import domain.gateway.remote.IRemoteCuisineGateway
import domain.gateway.remote.IRemoteMealGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
    singleOf(::FakeRemoteGateWay) { bind<IFakeRemoteGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
    singleOf(::RemoteCuisineGateway) { bind<IRemoteCuisineGateway>() }
    singleOf(::RemoteMealGateway) { bind<IRemoteMealGateway>() }
}