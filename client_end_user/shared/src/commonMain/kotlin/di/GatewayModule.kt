package di

import data.gateway.fake.FakeChatGateway
import data.gateway.fake.FakeOrderGateway
import data.gateway.fake.FakeNotificationGateway
import data.gateway.local.LocalConfigurationGateway
import data.gateway.remote.RestaurantGateway
import data.gateway.remote.UserGateway
import domain.gateway.IChatGateway
import domain.gateway.INotificationGateway
import domain.gateway.IOrderGateway
import domain.gateway.IRestaurantGateway
import domain.gateway.IUserGateway
import domain.gateway.local.ILocalConfigurationGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gatewayModule = module {
    singleOf(::FakeChatGateway) { bind<IChatGateway>() } // fake
    singleOf(::FakeNotificationGateway) { bind<INotificationGateway>() } // fake
    singleOf(::FakeOrderGateway) { bind<IOrderGateway>() } // fake
    singleOf(::RestaurantGateway) { bind<IRestaurantGateway>() } // remote
    singleOf(::UserGateway) { bind<IUserGateway>() } // remote
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }   // local
}