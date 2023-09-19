import dev.icerock.moko.permissions.ios.PermissionsController
import di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import data.BpLocationDataSource
import dev.icerock.moko.geo.LocationTracker

val permissions = module {
    single { BpLocationDataSource(LocationTracker(PermissionsController())) }
}

fun initKoin(){
    startKoin {
        modules(appModule(),permissions)
    }
}

