package di

import data.gateway.remote.BpLocationDataSource
import data.gateway.remote.FakeRemoteGateway
import domain.gateway.IBpLocationDataSource
import domain.gateway.IFakeRemoteGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gatewayModule = module {
    singleOf(::FakeRemoteGateway) { bind<IFakeRemoteGateway>() }
    singleOf(::BpLocationDataSource) { bind<IBpLocationDataSource>() }
}