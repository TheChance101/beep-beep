package com.beepbeep

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.location.LocationManager
import android.provider.Settings
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import data.service.ILocationService

class LocationService(private val context: Context): ILocationService {

    override suspend fun isGPSEnabled(): Boolean {
        val locationManager = getSystemService(context, LocationManager::class.java)
        val isGPSEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return isGPSEnabled == true
    }

    override fun openLocationSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        startActivity(context, intent, null)
    }
}
