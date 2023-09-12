import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.main.MainScreen
import presentation.resources.BpTaxiTheme

@Composable
fun App() {
    BpTaxiTheme {
        Navigator(MainScreen())
    }
}
