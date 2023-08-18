package org.thechance.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.beepbeep.designSystem.ui.theme.BpTheme
import org.thechance.common.presentation.login.LoginScreen


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    BpTheme {
        Navigator(LoginScreen){
            SlideTransition(it)
        }
    }
}

