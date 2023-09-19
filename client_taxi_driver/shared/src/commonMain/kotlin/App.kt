import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.map.MapScreen
import presentation.resources.BpTaxiTheme

@Composable
fun App() {
    BpTaxiTheme {
        Navigator(MapScreen())
    }
}
