package resources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.beepbeep.designSystem.ui.theme.BpTheme


private val localImageResources = staticCompositionLocalOf { BpPainterLightResources }
private val localStringResources = staticCompositionLocalOf { StringResources() }

@Composable
fun BpRestaurantTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val painterResources = if (useDarkTheme) BpPainterDarkResources else BpPainterLightResources

    CompositionLocalProvider(
        localImageResources provides painterResources,
        localStringResources provides StringResources()
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
        get() = localImageResources.current

    val strings: StringResources
        @Composable
        @ReadOnlyComposable
        get() = localStringResources.current
}