import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.login.LoginScreen
import presentation.resources.BpTaxiTheme

@Composable
fun App() {
    BpTaxiTheme {
        Navigator(LoginScreen())
    }
}
