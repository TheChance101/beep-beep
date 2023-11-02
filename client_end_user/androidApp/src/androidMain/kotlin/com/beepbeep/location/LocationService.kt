package com.beepbeep.location

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.core.content.ContextCompat
import data.gateway.service.ILocationService

class LocationService(private val context: Context) : ILocationService {

    override fun isDeviceLocationEnabled(): Boolean {
        val locationManager = ContextCompat.getSystemService(context, LocationManager::class.java)
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true || locationManager?.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        ) == true
    }

    override fun openLocationSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        ContextCompat.startActivity(context, intent, null)
    }
}