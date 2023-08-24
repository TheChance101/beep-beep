import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import presentation.restaurantSelection.RestaurantSelectionScreen
import resources.BpRestaurantTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    BpRestaurantTheme {
        Navigator(RestaurantSelectionScreen("550e8400-e29b-41d4-a716-446655440989")) {
            SlideTransition(it)
        }
    }
}
