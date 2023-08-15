package org.thechance.common

import androidx.compose.runtime.Composable
import com.beepbeep.designSystem.ui.theme.BpTheme
import org.thechance.common.ui.login.LoginContent

@Composable
fun App() {
    BpTheme() {
        LoginContent()
    }
}
