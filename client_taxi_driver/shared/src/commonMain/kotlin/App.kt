import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import di.appModule
import org.koin.core.context.startKoin
import presentation.main.MainScreen

@Composable
fun App() {
    startKoin { modules(appModule()) }
    Navigator(MainScreen())
}
