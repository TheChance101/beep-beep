import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.startKoin
import org.thechance.common.App
import di.appModule
import java.awt.Dimension


fun main() {
    startKoin { modules(appModule()) }
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "BeepBeep Dashboard",
            icon = painterResource("/ic_beep_beep_icon.xml"),
        ) {
            window.minimumSize = Dimension(1200, 600)
            App()
        }
    }
}
