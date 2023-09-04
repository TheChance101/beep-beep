
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import presentation.home.HomeScreen
import resources.BeepBeepTheme


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    BeepBeepTheme {
        Navigator(HomeScreen()) {
            SlideTransition(it)
        }
    }
}
