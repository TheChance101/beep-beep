package presentation.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import presentation.main.MainContainer
import presentation.pickLanguage.PickLanguageScreen
import resources.BeepBeepTheme

@Composable
fun App() {
    MainApp.Content()
}

object MainApp : Screen {
    @Composable
    override fun Content() {
        val appScreenModel = getScreenModel<AppScreenModel>()

        val userLanguage by appScreenModel.language.collectAsState()
        val firstTime by appScreenModel.isFirstTimeOpenApp.collectAsState()

        BeepBeepTheme(languageCode = userLanguage) {
            if (firstTime) {
                Navigator(PickLanguageScreen) { SlideTransition(it) }
            } else {
                Navigator(MainContainer) { SlideTransition(it) }
            }
        }
    }
}


