import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resources.BpRestaurantTheme
import resources.Resources

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    BpRestaurantTheme {
        Column(modifier = Modifier.fillMaxSize().background(Theme.colors.background)) {
            Image(painter = painterResource(Resources.images.bpIcon), contentDescription = null)
            Text(Resources.strings.beepBeep)
        }
    }
}