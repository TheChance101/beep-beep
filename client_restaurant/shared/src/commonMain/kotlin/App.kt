
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import presentation.restaurantSelection.RestaurantSelectionScreen
import resources.BpRestaurantTheme


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    BpRestaurantTheme(statusBarsVisible = false,navigationBarsVisible = false) {
        Navigator(RestaurantSelectionScreen()) {
            SlideTransition(it)
        }
    }
}
