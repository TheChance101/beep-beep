import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.thechance.common.App
import org.thechance.common.di.initKoin
import java.awt.Dimension


fun main() {
    initKoin()
    application {
        Window(onCloseRequest = ::exitApplication, title = "BeepBeep Dashboard") {
            window.minimumSize = Dimension(1200, 600)
            App()
        }
    }
}
