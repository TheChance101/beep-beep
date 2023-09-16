package util

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

actual class PlatformContext(val androidContext: Context)

@Composable
actual fun getPlatformContext(): PlatformContext = PlatformContext(LocalContext.current)

@Composable
actual fun SetInsetsController(isDark: Boolean) {
    val window = (getPlatformContext().androidContext as Activity).window
    WindowCompat.getInsetsController(window, window.decorView)
        .isAppearanceLightStatusBars = !isDark
}