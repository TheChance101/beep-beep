package util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController


private class AndroidWindowInsetsController(
    private val systemUiController: SystemUiController,
) : WindowInsetsController {
    override fun setStatusBarContentColor(color: Color, darkIcons: Boolean) {
        systemUiController.setStatusBarColor(color = color, darkIcons = darkIcons)
    }

    override fun setNavigationBarsContentColor(dark: Boolean) {
        systemUiController.navigationBarDarkContentEnabled = dark
    }

}

@Composable
actual fun rememberWindowInsetsController(): WindowInsetsController? {
    val systemUIController = rememberSystemUiController()
    return remember {
        AndroidWindowInsetsController(systemUIController)
    }
}
