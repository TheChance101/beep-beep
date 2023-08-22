import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import di.appModule
import org.koin.compose.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.login.LoginScreen
import presentation.main.MainScreen
import presentation.order.OrderScreen
import resources.BpRestaurantTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    startKoin { modules(appModule()) }
    BpRestaurantTheme {
        Navigator(OrderScreen()) {
            SlideTransition(it)
        }
    }
}