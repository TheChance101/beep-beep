package org.thechance.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.beepbeep.designSystem.ui.theme.BpTheme
import org.koin.java.KoinJavaComponent.inject
import org.thechance.common.presentation.app.AppScreenModel
import org.thechance.common.presentation.main.MainContainer
import org.thechance.common.presentation.resources.ProvideResources

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {

    val appScreenModel by inject<AppScreenModel>(AppScreenModel::class.java)
    val themeMode by appScreenModel.state.collectAsState()
    BpTheme(useDarkTheme = themeMode) {
        ProvideResources(isSystemInDarkTheme = themeMode) {
            Navigator(MainContainer) {
                SlideTransition(it)
            }
        }
    }

}


