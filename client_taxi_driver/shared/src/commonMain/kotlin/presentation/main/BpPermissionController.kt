package presentation.main

import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController

class BpPermissionController(val permissionsController: PermissionsController) {

    suspend fun getLocationPermission() {
            try {
                permissionsController.providePermission(Permission.LOCATION)
                println("location permission granted")
            } catch(deniedAlways: DeniedAlwaysException) {
                println("location permission deniedAlways: $deniedAlways")
            } catch(denied: DeniedException) {
                println("location permission denied: $denied")
            }
    }
}
