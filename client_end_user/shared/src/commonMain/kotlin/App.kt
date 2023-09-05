import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import resources.BeepBeepTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    BeepBeepTheme {
        Navigator(presentation.main.MainContainer) {
            SlideTransition(it)
        }
    }
}
