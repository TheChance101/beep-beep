package com.beepbeep

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        setContent {
//            val systemUIController = rememberSystemUiController()
//            systemUIController.setNavigationBarColor(color = Co)
//            systemUIController.setStatusBarColor(color = Color.Transparent)
            MainView()
        }
    }
}
