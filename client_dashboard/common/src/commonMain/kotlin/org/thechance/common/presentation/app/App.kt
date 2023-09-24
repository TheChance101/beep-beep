package org.thechance.common.presentation.app

import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.beepbeep.designSystem.ui.theme.BpTheme
import org.koin.java.KoinJavaComponent.inject
import org.thechance.common.presentation.login.LoginScreen
import org.thechance.common.presentation.resources.ProvideResources

@Composable
fun App() {

    val appScreenModel by remember { inject<AppScreenModel>(AppScreenModel::class.java) }
    val themeMode by appScreenModel.state.collectAsState()
    BpTheme(useDarkTheme = themeMode) {
        ProvideResources(isSystemInDarkTheme = themeMode) {
            Navigator(LoginScreen()) {
                SlideTransition(it)
            }
        }
    }
}
