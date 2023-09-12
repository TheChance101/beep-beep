import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.login.LoginScreen

@Composable
fun App() {
    Navigator(LoginScreen())
}
