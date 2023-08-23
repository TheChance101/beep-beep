import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import presentation.image.ImagePicker
import presentation.login.LoginScreen
import resources.BpRestaurantTheme

object Picker {
    lateinit var imagePicker: ImagePicker
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App(imagePicker: ImagePicker) {
    Picker.imagePicker = imagePicker

    BpRestaurantTheme {
        Navigator(LoginScreen()) {
            SlideTransition(it)
        }
    }
}

