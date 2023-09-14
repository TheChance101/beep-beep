import dev.icerock.moko.permissions.ios.PermissionsController
import di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.main.BpPermissionController

val permissions = module {
    single { BpPermissionController(PermissionsController()) }
}

fun initKoin(){
    startKoin {
        modules(appModule(),permissions)
    }
}

