import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.beepbeep.common.EmeraldApp


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        EmeraldApp()
    }
}
