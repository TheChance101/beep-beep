package org.thechance.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.beepbeep.designSystem.ui.theme.BpTheme
import org.thechance.common.ui.login.LoginScreen


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    BpTheme(useDarkTheme = false) {
        Navigator(LoginScreen){
            FadeTransition(it)
        }
    }
}
