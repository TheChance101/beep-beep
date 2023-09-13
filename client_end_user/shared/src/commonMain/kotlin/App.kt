import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import data.gateway.local.LocalConfigurationGateway
import kotlinx.coroutines.runBlocking
import org.koin.compose.getKoin
import presentation.pickLanguage.PickLanguageScreen
import resources.BeepBeepTheme

@Composable
fun App() {
    val localConfigurationGateway = LocalConfigurationGateway(getKoin().get())
    val languageCode = runBlocking { localConfigurationGateway.getLanguageCode() }
    BeepBeepTheme(languageCode = languageCode) {
        Navigator(PickLanguageScreen) {
            SlideTransition(it)
        }
    }
}
