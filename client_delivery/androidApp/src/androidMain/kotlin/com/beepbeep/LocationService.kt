package com.beepbeep
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import data.service.ILocationService

class LocationService(private val context: Context): ILocationService {
  @RequiresApi(Build.VERSION_CODES.S)

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
}