package org.thechance.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.beepbeep.designSystem.ui.theme.BpTheme
import org.koin.java.KoinJavaComponent.inject
import org.thechance.common.presentation.app.ThemeScreenModel
import org.thechance.common.presentation.main.MainContainer
import org.thechance.common.presentation.resources.ProvideResources

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    val themeScreenModel by inject<ThemeScreenModel>(ThemeScreenModel::class.java)
    val themeMode by themeScreenModel.state.collectAsState()
    BpTheme(useDarkTheme = themeMode) {
        ProvideResources {
            Navigator(MainContainer) {
                SlideTransition(it)
            }
        }
    }
}


