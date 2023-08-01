import androidx.compose.ui.window.ComposeUIViewController
import
actual fun getPlatformName(): String = "iOS"

fun MainViewController() = ComposeUIViewController { App() }