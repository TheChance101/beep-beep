import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.beepbeep.designSystem.ui.theme.BpTheme
import di.appModule
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatform
import presentation.login.LoginScreen
import presentation.login.LoginScreenModel

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
