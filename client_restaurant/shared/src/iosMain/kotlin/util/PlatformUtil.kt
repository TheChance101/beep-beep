package util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication

@Composable
actual fun setInsetsController(isDark: Boolean) {
}

@Composable
actual fun getNavigationBarPadding(): PaddingValues {
    return PaddingValues(bottom = 30.dp)
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun getStatusBarPadding(): PaddingValues {
    val statusBarSize = UIApplication.sharedApplication.statusBarFrame.size
    return PaddingValues(top = statusBarSize.dp)
}


actual fun getEngine(): HttpClientEngine = Darwin.create()
