import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.beepbeep.designSystem.DesignApp


fun main() {
    application {
        Window(onCloseRequest = ::exitApplication) {
            DesignApp()
        }
    }
}
