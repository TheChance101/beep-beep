package com.beepbeep.designSystem

import androidx.compose.runtime.Composable

actual fun getPlatformName(): String = "desktop"

@Composable
fun MainView() = DesignApp()