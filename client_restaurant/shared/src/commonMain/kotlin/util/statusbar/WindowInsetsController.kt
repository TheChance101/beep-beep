package util.statusbar

import androidx.compose.runtime.Composable

interface WindowInsetsController {

    fun setStatusBarContentColor(dark: Boolean)


    fun setNavigationBarsContentColor(dark: Boolean)


    fun setIsStatusBarsVisible(isVisible: Boolean)


    fun setIsNavigationBarsVisible(isVisible: Boolean)


    fun setSystemBarsBehavior(behavior: SystemBarsBehavior)
}

@Composable
expect fun rememberWindowInsetsController(): WindowInsetsController?