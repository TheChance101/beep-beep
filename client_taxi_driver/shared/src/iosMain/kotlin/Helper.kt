import dev.icerock.moko.permissions.ios.PermissionsController
import di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(permission: PermissionsController){
    startKoin {
        modules(appModule(), initPermission(permission))
    }
}

fun initPermission(permission: PermissionsController) = module {
    single { permission }
}
