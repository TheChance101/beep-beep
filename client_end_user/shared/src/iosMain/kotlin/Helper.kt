import data.gateway.local.LocationGateway
import data.gateway.service.IFireBaseMessageService
import data.gateway.service.ILocationService
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.ios.PermissionsController
import di.appModule
import domain.gateway.local.ILocationGateway
import org.koin.core.context.startKoin
import org.koin.dsl.module


val notificationModule = module {
    single<IFireBaseMessageService> { FireBaseMessageService() }
}

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
            locationTrackerModule,
            notificationModule
        )
    }
}

