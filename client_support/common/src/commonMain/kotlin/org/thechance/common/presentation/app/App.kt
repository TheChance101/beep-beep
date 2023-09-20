package org.thechance.common.presentation.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.beepbeep.designSystem.DesignApp
import com.beepbeep.designSystem.ui.theme.BpTheme
import org.thechance.common.presentation.login.LoginScreen
import org.thechance.common.presentation.resources.ProvideResources

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
