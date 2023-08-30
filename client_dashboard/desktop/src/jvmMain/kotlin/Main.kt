import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.sun.javafx.application.PlatformImpl
import di.appModule
import org.koin.core.context.startKoin
import org.thechance.common.App
import java.awt.Dimension


fun main() {
    startKoin { modules(appModule()) }
    application {
        val finishListener = object : PlatformImpl.FinishListener {
            override fun idle(implicitExit: Boolean) {}
            override fun exitCalled() {}
        }
        PlatformImpl.addListener(finishListener)
        Window(
            onCloseRequest = {
                PlatformImpl.removeListener(finishListener)
                exitApplication()
            },
            title = "BeepBeep Dashboard",
            icon = painterResource("ic_beepbeep_logo.svg"),
        ) {
            window.minimumSize = Dimension(1200, 600)
            App()
        }
    }
}
