package org.thechance.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.beepbeep.designSystem.ui.composable.BeepBeepButton
import org.thechance.common.ui.login.LoginContent


@Composable
fun App() {
    val platformName = getPlatformName()
    if (platformName == "Desktop") LoginContent() else AndroidLogin()
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AndroidLogin() {
    BeepBeepButton(onClick = {}) {
        Text("Login")
    }
}