package util

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat

@Composable
actual fun setInsetsController(isDark: Boolean) {
    val window = (getPlatformContext().androidContext as Activity).window
    WindowCompat.getInsetsController(window, window.decorView)
        .isAppearanceLightStatusBars = !isDark

}
