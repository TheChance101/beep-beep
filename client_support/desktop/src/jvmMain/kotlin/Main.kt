import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.appModule
import org.koin.core.context.startKoin
import org.thechance.common.presentation.app.App
import java.awt.Dimension

fun main() {
    startKoin { modules(appModule()) }
    application {
        Window(
            onCloseRequest = {
                exitApplication()
            },
            title = "BeepBeep Support",
            icon = painterResource("ic_beepbeep_logo.svg"),
        ) {
            window.minimumSize = Dimension(1200, 600)
            App()
        }
    }
}
