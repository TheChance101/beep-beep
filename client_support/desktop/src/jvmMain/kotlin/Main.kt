import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.thechance.common.presentation.app.App

fun main() {
    application {
        Window(onCloseRequest = ::exitApplication) {
            App()
        }
    }
}
