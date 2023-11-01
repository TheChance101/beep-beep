package com.beepbeep

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.beepbeep.notification.FCMNotificationImp
import com.beepbeep.notification.ServiceLocator
import com.google.firebase.FirebaseApp
import dev.icerock.moko.geo.LocationTracker
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val locationTracker by inject<LocationTracker>()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        ServiceLocator.initialize(FCMNotificationImp(applicationContext))
        locationTracker.bind(lifecycle, this, supportFragmentManager)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            this.window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
            this.window.navigationBarColor =
                ContextCompat.getColor(this, android.R.color.transparent)
            MainView()
        }
    }
}
