package com.beepbeep

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import dev.icerock.moko.geo.LocationTracker

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locationTracker by inject<LocationTracker>()
        locationTracker.bind(lifecycle, this, supportFragmentManager)

        setContent {
            MainView()
        }
    }
}
