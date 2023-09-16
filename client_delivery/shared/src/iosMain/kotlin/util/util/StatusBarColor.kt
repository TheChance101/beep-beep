package util.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.interop.LocalUIViewController

actual class PlatformContext(val iosController: ProvidableCompositionLocal<UIViewController>)

@Composable
actual fun getPlatformContext(): PlatformContext = util.PlatformContext(LocalUIViewController)

@Composable
actual fun setInsetsController(isDark: Boolean) {

}