package app.di

import data.remote.service.FakeApiService
import data.remote.service.FakeApiServiceImp
import org.koin.dsl.module

val networkModule = module {

    single<FakeApiService> { FakeApiServiceImp() }

}