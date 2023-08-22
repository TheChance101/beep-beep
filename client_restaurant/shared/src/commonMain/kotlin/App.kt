
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import di.appModule
import org.koin.compose.KoinApplication
import presentation.login.LoginScreen
import resources.BpRestaurantTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    KoinApplication(application = {
        modules(appModule())
    }) {
        BpRestaurantTheme {
            Navigator(LoginScreen()){
                SlideTransition(it)
            }
        }
    }
}