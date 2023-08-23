package di

import data.remote.gateway.FakeRemoteGateWay
import domain.gateway.IRemoteGateWay
import org.koin.dsl.module

val GatewayModule = module {
    single<IRemoteGateWay> { FakeRemoteGateWay() }
}