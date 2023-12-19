package di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import presentation.util.IPagingSource
import presentation.util.pagesource.FoodOrderPagingSource
import presentation.util.pagesource.MealsPagingSource
import presentation.util.pagesource.NotificationPagingSource
import presentation.util.pagesource.PagingSource
import presentation.util.pagesource.RestaurantsPagingSource
import presentation.util.pagesource.TaxiOrderPagingSource

val pagingDataSourceModule = module {
    singleOf(::MealsPagingSource)
    singleOf(::FoodOrderPagingSource)
    singleOf(::TaxiOrderPagingSource)
    singleOf(::RestaurantsPagingSource)
    singleOf(::NotificationPagingSource)
    singleOf(::PagingSource) { bind<IPagingSource>() }
}