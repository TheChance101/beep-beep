package presentation.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import util.rememberWindowInsetsController

@Composable
fun StatusBar(color: Color) {
    val windowInsetsController = rememberWindowInsetsController()
    val isDarkThem = isSystemInDarkTheme()
    LaunchedEffect(isDarkThem) {
        windowInsetsController?.setStatusBarContentColor(color = color, darkIcons = !isDarkThem)
    }
}
