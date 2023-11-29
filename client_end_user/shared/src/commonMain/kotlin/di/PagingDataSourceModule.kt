package di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import data.gateway.remote.pagesource.*
import org.koin.core.module.dsl.bind
import presentation.util.IPagingDataSource

val pagingDataSourceModule = module {
    singleOf(::MealsPagingSource)
    singleOf(::FoodOrderPagingSource)
    singleOf(::TaxiOrderPagingSource)
    singleOf(::RestaurantsPagingSource)
    singleOf(::NotificationPagingSource)
    singleOf(::PagingDataSource)
    singleOf(::PagingDataSource) { bind<IPagingDataSource>() }
}