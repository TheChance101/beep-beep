import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.ios.PermissionsController
import di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

val locationTrackerModule = module {
    single { LocationTracker(PermissionsController()) }
}

fun initKoin() {
    startKoin {
        modules(appModule(), locationTrackerModule)
    }
}