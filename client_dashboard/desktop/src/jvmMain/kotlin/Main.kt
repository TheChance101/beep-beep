import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.beepbeep.designSystem.App


fun main() {
    application {
        Window(onCloseRequest = ::exitApplication) {
            App()
        }
    }
}
