package util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.unit.dp
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import platform.UIKit.UIViewController

actual class PlatformContext(val iosController: ProvidableCompositionLocal<UIViewController>)

@Composable
actual fun getPlatformContext(): PlatformContext = PlatformContext(LocalUIViewController)

@Composable
actual fun SetInsetsController(isDark: Boolean) {
}

@Composable
actual fun getNavigationBarPadding(): PaddingValues {
    return PaddingValues(bottom = 10.dp)
}

actual fun getEngine(): HttpClientEngine = Darwin.create()
