package di

import data.gateway.fake.FakeChatGateway
import data.gateway.local.LocalConfigurationGateway
import data.gateway.local.LocalRestaurantGateway
import data.gateway.local.LocationGateway
import data.gateway.remote.RestaurantGateway
import data.gateway.remote.TransactionsGateway
import data.gateway.remote.UserGateway
import data.gateway.remote.pagesource.FoodOrderPagingSource
import data.gateway.remote.pagesource.MealsPagingSource
import data.gateway.remote.pagesource.NotificationPagingSource
import data.gateway.remote.pagesource.TaxiOrderPagingSource
import domain.gateway.IChatGateway
import domain.gateway.IRestaurantGateway
import domain.gateway.ITransactionsGateway
import domain.gateway.IUserGateway
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.local.ILocalRestaurantGateway
import domain.gateway.local.ILocationGateway
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gatewayModule = module {
    singleOf(::FakeChatGateway) { bind<IChatGateway>() } // fake
    singleOf(::TransactionsGateway) { bind<ITransactionsGateway>() }
    singleOf(::RestaurantGateway) { bind<IRestaurantGateway>() }
    singleOf(::UserGateway) { bind<IUserGateway>() }
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
    singleOf(::LocalRestaurantGateway) { bind<ILocalRestaurantGateway>() }
    singleOf(::FoodOrderPagingSource)
    singleOf(::TaxiOrderPagingSource)
    singleOf(::LocalConfigurationGateway) { bind<ILocalConfigurationGateway>() }
    singleOf(::LocationGateway) { bind<ILocationGateway>() }
    singleOf(::MealsPagingSource)
    singleOf(::NotificationPagingSource)
}
