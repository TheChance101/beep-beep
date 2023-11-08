package util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.unit.dp
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import platform.Foundation.NSBundle
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

actual fun getMapUrl(): String {
    val mainBundle = NSBundle.mainBundle
    val x =mainBundle.bundleURL
    println(x)
    val filePath = mainBundle.pathForResource("index", "html", "bing_map")
    return filePath?.let { "file://$it" } ?: ""
}
