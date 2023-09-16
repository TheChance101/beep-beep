package presentation.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import kotlinx.coroutines.runBlocking
import org.koin.compose.getKoin
import presentation.main.MainContainer
import presentation.pickLanguage.PickLanguageScreen
import resources.BeepBeepTheme

@Composable
fun App() {
    val appScreenModel = AppScreenModel(getKoin().get())
    var isFirstTime by remember { mutableStateOf(true) }
    var userLanguageCode by remember { mutableStateOf("en") }

    runBlocking {
//        isFirstTime = appScreenModel.getInitScreen()
        userLanguageCode = appScreenModel.getUserLanguageCode()
    }

    BeepBeepTheme(languageCode = userLanguageCode) {
        if (isFirstTime) {
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


