package util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.interop.LocalUIViewController
import platform.UIKit.UIViewController

actual class PlatformContext(val iosController: ProvidableCompositionLocal<UIViewController>)

@Composable
actual fun getPlatformContext(): PlatformContext = PlatformContext(LocalUIViewController)

@Composable
actual fun SetInsetsController(isDark: Boolean) {}
