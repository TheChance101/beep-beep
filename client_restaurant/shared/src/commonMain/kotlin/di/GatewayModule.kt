package di

import data.gateway.RestaurantGateWay
import data.remote.gateway.FakeRemoteGateWay
import domain.gateway.IRemoteGateWay
import org.koin.dsl.module

val GatewayModule = module {
    single<IRemoteGateWay> { FakeRemoteGateWay() }
    single<IRemoteGateWay> { RestaurantGateWay(get()) }
}