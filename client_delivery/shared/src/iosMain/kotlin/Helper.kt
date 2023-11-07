import di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import data.gateway.local.LocationGateway
import data.service.ILocationService
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.ios.PermissionsController
import domain.gateway.local.ILocationGateway


val locationDataSourceModule = module {
    single<ILocationGateway> { LocationGateway(get(), get()) }
}

val locationServiceModule = module {
    single<ILocationService> { LocationService() }
}

val locationTrackerModule = module {
    single { LocationTracker(PermissionsController()) }
}

fun initKoin() {
    startKoin {
        modules(
            appModule(),
            locationDataSourceModule,
            locationServiceModule,
            locationTrackerModule
        )
    }
}
