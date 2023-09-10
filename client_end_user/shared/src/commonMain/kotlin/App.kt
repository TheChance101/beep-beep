import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import presentation.main.MainContainer
import resources.BeepBeepTheme


@Composable
fun App() {
    BeepBeepTheme {
        Navigator(MainContainer) {
            SlideTransition(it)
        }
    }
}
