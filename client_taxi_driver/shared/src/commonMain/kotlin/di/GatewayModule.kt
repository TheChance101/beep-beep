package di

import data.remote.fakegateway.MapFakeGateway
import domain.gateway.IMapGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
    singleOf(::MapFakeGateway) { bind<IMapGateway>() }
}