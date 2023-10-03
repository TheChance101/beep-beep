import dev.icerock.moko.permissions.ios.PermissionsController
import di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import data.BpLocationDataSource
import data.service.ILocationService
import dev.icerock.moko.geo.LocationTracker
import domain.dataSource.IBpLocationDataSource

val locationDataSourceModule = module {
    single<IBpLocationDataSource> { BpLocationDataSource(get()) }
}

val locationTrackerModule = module {
    single { LocationTracker(PermissionsController()) }
}

val locationServiceModule = module {
    single<ILocationService> { LocationService() }
}

fun initKoin() {
    startKoin {
        modules(
            appModule(),
            locationDataSourceModule,
            locationTrackerModule,
            locationServiceModule
        )
    }
}

