import di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.module


val locationDataSourceModule = module {
    single<IBpLocationDataSource> { BpLocationDataSource(get()) }
}

val locationTrackerModule = module {
    single { LocationTracker(PermissionsController()) }
}

fun initKoin() {
    startKoin {
        modules(appModule())
        locationDataSourceModule,
        locationTrackerModule
    }
}