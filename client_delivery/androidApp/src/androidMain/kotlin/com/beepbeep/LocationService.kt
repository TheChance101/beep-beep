package com.beepbeep
import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import data.service.ILocationService

class LocationService(private val context: Context): ILocationService {
  @RequiresApi(Build.VERSION_CODES.S)
  private val locationRequest =
        LocationRequest.Builder(10000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setMinUpdateIntervalMillis(100).setMaxUpdates(1).build()

    @SuppressLint("SuspiciousIndentation")
    /*  private val locationSettingPermissionResultRequest =
      registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
               // getMyCurrentLocation()
            }
            //else _viewModel.showErrorMessage.value = getString(R.string.deny_to_open_location)
        }*/

    override fun isDeviceLocationEnabled(): Boolean {
        val locationManager = getSystemService(context, LocationManager::class.java)
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true || locationManager?.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        ) == true
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun openLocationSettings() {
          val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        startActivity(context, intent, null)
    }
//         val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
//        val client: SettingsClient = LocationServices.getSettingsClient(context)
//        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
//        task.addOnSuccessListener { locationSettingsResponse ->
//            // All location settings are satisfied. The client can initialize
//            // location requests here.
//            // ...
//            //getMyCurrentLocation()
//        }
//        task.addOnFailureListener { exception ->
//            if (exception is ResolvableApiException) {
//                // Location settings are not satisfied, but this can be fixed
//                // by showing the user a dialog.
//                try {
//                    // Show the dialog by calling startResolutionForResult(),
//                    // and check the result in onActivityResult().
////                    locationSettingPermissionResultRequest.launch(
////                        IntentSenderRequest.Builder(exception.resolution.intentSender).build()
////                    )
//                } catch (sendEx: IntentSender.SendIntentException) {
//                    // Ignore the error.
//                }
//            }
//        }
//    }
}