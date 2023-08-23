package di

import data.gateway.FakeRemoteGateWay
import data.gateway.RestaurantGateWay
import domain.gateway.IRemoteGateWay
import domain.gateway.IRestaurantGateWay
import org.koin.dsl.module

val GatewayModule = module {
    single<IRemoteGateWay> { FakeRemoteGateWay() }
    single<IRestaurantGateWay> { RestaurantGateWay(get()) }
}