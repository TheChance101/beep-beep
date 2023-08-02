package com.beepbeep.designSystem

import androidx.compose.runtime.Composable

actual fun getPlatformName(): String = "Android"

@Composable fun MainView() = DesignApp()
