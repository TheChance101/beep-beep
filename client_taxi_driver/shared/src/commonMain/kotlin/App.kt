import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.main.MainScreen

@Composable
fun App() {
    Navigator(MainScreen())
}
