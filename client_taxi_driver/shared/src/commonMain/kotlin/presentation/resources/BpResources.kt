package presentation.resources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.beepbeep.designSystem.ui.theme.BpTheme

private val localDrawableResources = staticCompositionLocalOf<DrawableResources> {
    throw Exception("drawable resources is not provided")
}

private val localStringResources = staticCompositionLocalOf<StringResources> {
    throw Exception("string resources is not provided")
}

@Composable
fun BpTaxiTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val drawableResources = if (isDarkTheme) darkDrawableResources else DrawableResources()

    CompositionLocalProvider(
        localDrawableResources provides drawableResources,
        localStringResources provides StringResources()
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
