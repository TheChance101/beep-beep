package di

import data.local.source.FakeLocalDataSourceImp
import data.remote.source.FakeRemoteDataSourceImp
import domain.source.FakeLocalDataSource
import domain.source.FakeRemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<FakeRemoteDataSource> {
        FakeRemoteDataSourceImp(get())
    }

    single<FakeLocalDataSource> {
        FakeLocalDataSourceImp()
    }
}