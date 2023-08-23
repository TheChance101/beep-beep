import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.thechance.common.App
import org.thechance.common.di.appModule
import java.awt.Dimension


fun main() {
    startKoin { modules(appModule()) }
    application {
        Window(onCloseRequest = ::exitApplication, title = "BeepBeep Dashboard") {
            window.minimumSize = Dimension(1200, 600)
            App()
        }
    }
}
