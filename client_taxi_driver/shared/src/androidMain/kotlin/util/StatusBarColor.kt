package util

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
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
@Composable
actual fun getNavigationBarPadding(): PaddingValues {
    return WindowInsets.navigationBars.asPaddingValues()
}


@Composable
actual fun getStatusBarPadding(): PaddingValues {
    return WindowInsets.statusBars.asPaddingValues()
}