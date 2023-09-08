package util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface WindowInsetsController {

     fun setStatusBarContentColor(color: Color, darkIcons: Boolean)
     fun setNavigationBarsContentColor(dark: Boolean)

}

@Composable
expect fun rememberWindowInsetsController(): WindowInsetsController?
