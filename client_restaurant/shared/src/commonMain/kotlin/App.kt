
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.beepbeep.designSystem.ui.theme.BpTheme
import di.appModule
import org.koin.compose.KoinApplication
import presentation.login.LoginScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    KoinApplication(application = {
        modules(appModule())
    }) {
        BpTheme {
            Navigator(LoginScreen()) {
                SlideTransition(it)
            }
        }
    }
}