package presentation.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import kotlinx.coroutines.runBlocking
import presentation.main.MainContainer
import presentation.pickLanguage.PickLanguageScreen
import resources.BeepBeepTheme
import util.LanguageCode

@Composable
fun App() { Navigator(MainApp) }

object MainApp : Screen {
    @Composable
    override fun Content() {
        val appScreenModel = getScreenModel<AppScreenModel>()

        val userLanguage by appScreenModel.language.collectAsState()

        BeepBeepTheme(languageCode = userLanguage) {
            if (true) {
                Navigator(PickLanguageScreen) {
                    SlideTransition(it)
                }
            } else {
                Navigator(MainContainer) {
                    SlideTransition(it)
                }
            }
        }
    }
}


