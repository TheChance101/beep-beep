package util

import android.app.Activity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import java.lang.reflect.Modifier

@Composable
actual fun setInsetsController(isDark: Boolean) {
    val window = (getPlatformContext().androidContext as Activity).window

    WindowCompat.getInsetsController(window, window.decorView)
        .isAppearanceLightStatusBars = !isDark

    WindowCompat.getInsetsController(window, window.decorView)
        .isAppearanceLightNavigationBars = !isDark
}

@Composable
actual fun getNavigationBarPadding(): PaddingValues {
    return WindowInsets.navigationBars.asPaddingValues()
}


@Composable
actual fun getStatusBarPadding(): PaddingValues {
    return WindowInsets.statusBars.asPaddingValues()
}
