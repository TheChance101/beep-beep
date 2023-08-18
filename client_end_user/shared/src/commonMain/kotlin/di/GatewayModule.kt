package di

import data.gateway.FakeGatewayImp
import domain.gateway.FakeGateway
import org.koin.dsl.module

val gatewayModule = module {
    single<FakeGateway> { FakeGatewayImp(get(), get()) }
}