import androidx.compose.runtime.Composable
import di.AppModule.initKoin
import cafe.adriel.voyager.navigator.Navigator
import presentation.screens.home.HomeScreen

@Composable
fun App() {
    initKoin()
    Navigator(HomeScreen())
}