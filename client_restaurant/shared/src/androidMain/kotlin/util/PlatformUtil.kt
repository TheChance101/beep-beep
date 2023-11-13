package util

import android.app.Activity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import io.ktor.client.engine.cio.CIO

@Composable
actual fun getNavigationBarPadding(): PaddingValues {
    return WindowInsets.navigationBars.asPaddingValues()
}
@Composable
actual fun getStatusBarPadding(): PaddingValues {
    return WindowInsets.statusBars.asPaddingValues()
}
@Composable
actual fun setInsetsController(isDark: Boolean) {
    val window = (getPlatformContext().androidContext as Activity).window

    WindowCompat.getInsetsController(window, window.decorView)
        .isAppearanceLightStatusBars = !isDark

    WindowCompat.getInsetsController(window, window.decorView)
        .isAppearanceLightNavigationBars = !isDark
}


actual fun getEngine() = CIO.create()
