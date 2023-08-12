package org.thechance.common

import androidx.compose.runtime.Composable
import org.thechance.common.ui.login.LoginContent


@Composable
fun App() {
    val platformName = getPlatformName()
    LoginContent()
}
