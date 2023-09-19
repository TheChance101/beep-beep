package di

import data.remote.fakegateway.LocationFakeGateway
import data.remote.fakegateway.OrderFakeGateway
import domain.gateway.ILocationGateway
import domain.gateway.IOrderGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val GatewayModule = module {
    singleOf(::OrderFakeGateway) { bind<IOrderGateway>() }
    singleOf(::LocationFakeGateway) { bind<ILocationGateway>() }
}