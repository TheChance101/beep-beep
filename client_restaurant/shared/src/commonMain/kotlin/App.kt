
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import presentation.login.LoginScreen
import resources.BpRestaurantTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    BpRestaurantTheme {
        Navigator(LoginScreen()) {
            SlideTransition(it)
        }
    }
}
