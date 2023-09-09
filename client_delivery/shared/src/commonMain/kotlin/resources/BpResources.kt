package resources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.beepbeep.designSystem.ui.theme.BpTheme

private val localDrawableResources = staticCompositionLocalOf { DrawableResources() }
private val localStringResources = staticCompositionLocalOf { StringResources() }


@Composable
fun BeepBeepTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val drawableResources = if (useDarkTheme) BpDrawableDarkResources else DrawableResources()

    CompositionLocalProvider(
        localDrawableResources provides drawableResources,
        localStringResources provides StringResources(),
    ) {
        BpTheme {
            content
        }
    }
}

object Resources {
    val image: DrawableResources
        @Composable
        @ReadOnlyComposable
        get() = localDrawableResources.current

    val strings: StringResources
        @Composable
        @ReadOnlyComposable
        get() = localStringResources.current
}