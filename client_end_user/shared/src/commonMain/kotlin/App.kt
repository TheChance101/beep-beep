import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import resources.BeepBeepTheme


@Composable
fun App() {
    BeepBeepTheme {
        Navigator(presentation.main.MainContainer) {
            SlideTransition(it)
        }
    }
}
