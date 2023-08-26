package di

import data.fake.FakeFakeRemoteGateWay
import data.local.LocalConfigurationGateway
import data.remote.gateway.RemoteIdentityGateway
import domain.gateway.IFakeRemoteGateway
import domain.gateway.ILocalConfigurationGateway
import domain.gateway.IRemoteIdentityGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
    //todo add remaining gateway
    singleOf(::FakeFakeRemoteGateWay) { bind<IFakeRemoteGateway>() }
    singleOf(::RemoteIdentityGateway) { bind<IRemoteIdentityGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
}