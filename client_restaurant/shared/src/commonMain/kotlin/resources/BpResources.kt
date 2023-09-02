package resources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.beepbeep.designSystem.ui.theme.BpTheme
import com.seiko.imageloader.LocalImageLoader
import util.generateImageLoader
import util.getPlatformContext
import util.statusbar.rememberWindowInsetsController

private val localDrawableResources = staticCompositionLocalOf { DrawableResources() }
private val localStringResources = staticCompositionLocalOf { StringResources() }

@Composable
fun BpRestaurantTheme(
    statusBarsVisible : Boolean = true,
    navigationBarsVisible : Boolean = true,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val drawableResources = if (useDarkTheme) BpDrawableDarkResources else DrawableResources()
    val context = getPlatformContext()

    val windowInsetsController = rememberWindowInsetsController()
    LaunchedEffect(useDarkTheme) {
        windowInsetsController?.setIsNavigationBarsVisible(navigationBarsVisible)
        windowInsetsController?.setIsStatusBarsVisible(statusBarsVisible)
        windowInsetsController?.setSystemBarsBehavior(util.statusbar.SystemBarsBehavior.Immersive)
    }

    CompositionLocalProvider(
        localDrawableResources provides drawableResources,
        localStringResources provides StringResources(),
        LocalImageLoader provides remember { generateImageLoader(context) }
    ) {
        BpTheme {
            content()
        }
    }
}

object Resources {
    val images: DrawableResources
        @Composable
        @ReadOnlyComposable
        get() = localDrawableResources.current

    val strings: StringResources
        @Composable
        @ReadOnlyComposable
        get() = localStringResources.current
}