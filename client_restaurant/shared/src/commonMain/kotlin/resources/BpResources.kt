package resources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.beepbeep.designSystem.ui.theme.BpTheme
import com.seiko.imageloader.LocalImageLoader
import util.generateImageLoader
import util.getPlatformContext

private val localPainterResources = staticCompositionLocalOf { BpPainterLightResources }
private val localStringResources = staticCompositionLocalOf { StringResources() }

@Composable
fun BpRestaurantTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val painterResources = if (useDarkTheme) BpPainterDarkResources else BpPainterLightResources
    val context = getPlatformContext()

    CompositionLocalProvider(
        localPainterResources provides painterResources,
        localStringResources provides StringResources(),
        LocalImageLoader provides remember { generateImageLoader(context) }
    ) {
        BpTheme {
            content()
        }
    }
}

object Resources {
    val images: PainterResources
        @Composable
        @ReadOnlyComposable
        get() = localPainterResources.current

    val strings: StringResources
        @Composable
        @ReadOnlyComposable
        get() = localStringResources.current
}