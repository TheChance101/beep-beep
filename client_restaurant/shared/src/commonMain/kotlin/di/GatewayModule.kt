package di

import data.remote.gateway.FakeRemoteGateWay
import domain.gateway.IRemoteGateWay
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
    singleOf(::FakeRemoteGateWay){ bind<IRemoteGateWay>()}
}