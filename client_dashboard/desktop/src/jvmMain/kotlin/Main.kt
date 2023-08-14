import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.thechance.common.App
import java.awt.Dimension


fun main() {
    application {
        Window(onCloseRequest = ::exitApplication, title = "BeepBeep Dashboard") {
            window.minimumSize = Dimension(1024, 600)
            App()
        }
    }
}
