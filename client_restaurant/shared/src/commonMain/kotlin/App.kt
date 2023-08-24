import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import presentation.order.order_history.OrdersHistoryScreen
import resources.BpRestaurantTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    BpRestaurantTheme {
        Navigator(OrdersHistoryScreen("6ab493b4-4b8d-410a-a13e-780346243f3a")) {
            SlideTransition(it)
        }
    }
}
