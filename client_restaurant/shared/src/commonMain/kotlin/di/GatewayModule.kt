package di

import data.local.gateway.LocalGateWay
import data.remote.gateway.FakeRemoteGateWay
import data.remote.gateway.RemoteGateWay
import domain.gateway.IFakeGateWay
import domain.gateway.ILocalGateWay
import domain.gateway.IRemoteGateWay
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
    singleOf(::FakeRemoteGateWay) { bind<IFakeGateWay>() }
    singleOf(::RemoteGateWay) { bind<IRemoteGateWay>() }
    singleOf(::LocalGateWay) { bind<ILocalGateWay>() }
}