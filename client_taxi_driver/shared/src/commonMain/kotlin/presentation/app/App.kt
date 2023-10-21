package presentation.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import presentation.login.LoginScreen
import presentation.main.MainScreen
import presentation.resources.BpTaxiTheme

@Composable
fun App() {
    MainApp.Content()
}

object MainApp : Screen {
    @Composable
    override fun Content() {
        val appScreenModel = getScreenModel<AppScreenModel>()

        val isKeptLoggedIn by appScreenModel.isKeptLoggedIn.collectAsState()
        println("isKeptLoggedIn: $isKeptLoggedIn")
        BpTaxiTheme {
            if (isKeptLoggedIn) {
                Navigator(MainScreen()) { SlideTransition(it) }
            } else {
                Navigator(LoginScreen()) { SlideTransition(it) }
            }
        }
    }
}
