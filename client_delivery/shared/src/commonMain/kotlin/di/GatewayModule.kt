package di

import data.gateway.FakeRemoteGateway
import domain.gateway.IFakeRemoteGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gatewayModule = module {
    singleOf(::FakeRemoteGateway) { bind<IFakeRemoteGateway>() }
}