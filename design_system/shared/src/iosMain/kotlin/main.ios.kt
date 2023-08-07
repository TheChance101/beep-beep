package com.beepbeep.designSystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

actual fun getPlatformName(): String = "iOS"

fun MainViewController() = ComposeUIViewController { DesignApp() }


