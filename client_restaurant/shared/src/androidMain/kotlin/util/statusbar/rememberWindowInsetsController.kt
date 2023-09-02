package util.statusbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController


private class AndroidWindowInsetsController(
    private val systemUiController: SystemUiController,
) : WindowInsetsController {
    override fun setStatusBarContentColor(dark: Boolean) {
        systemUiController.statusBarDarkContentEnabled = dark
    }

    override fun setNavigationBarsContentColor(dark: Boolean) {
        systemUiController.navigationBarDarkContentEnabled = dark
    }

    override fun setIsStatusBarsVisible(isVisible: Boolean) {
        systemUiController.isStatusBarVisible = isVisible
    }

    override fun setIsNavigationBarsVisible(isVisible: Boolean) {
        systemUiController.isNavigationBarVisible = isVisible
    }

    override fun setSystemBarsBehavior(behavior: SystemBarsBehavior) {
        systemUiController.systemBarsBehavior = behavior.value
    }
}

@Composable
actual fun rememberWindowInsetsController(): WindowInsetsController? {
    val systemUIController = rememberSystemUiController()
    return remember {
        AndroidWindowInsetsController(systemUIController)
    }
}