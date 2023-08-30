package org.thechance.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.beepbeep.designSystem.ui.theme.BpTheme
import org.thechance.common.presentation.login.LoginScreen
import org.thechance.common.presentation.main.MainContainer
import org.thechance.common.presentation.resources.ProvideResources

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    BpTheme {
        ProvideResources {
            Navigator(LoginScreen()) {
                SlideTransition(it)
            }
        }
    }
}
