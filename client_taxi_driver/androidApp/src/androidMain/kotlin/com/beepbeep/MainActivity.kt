package com.beepbeep

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import presentation.main.BpPermissionController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionController by inject<BpPermissionController>()
        permissionController.permissionsController.bind(lifecycle, supportFragmentManager)

        setContent {
            MainView()
        }
    }
}
