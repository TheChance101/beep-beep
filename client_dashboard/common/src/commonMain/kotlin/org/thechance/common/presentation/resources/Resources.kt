package org.thechance.common.presentation.resources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val stringResources = staticCompositionLocalOf { Strings }
private val drawables = staticCompositionLocalOf { lightDrawableResource }

object Resources {

    val Strings: StringResources
        @Composable
        @ReadOnlyComposable
        get() = stringResources.current

    val Drawable: DrawableResources
        @Composable
        @ReadOnlyComposable
        get() = drawables.current

}

@Composable
fun ProvideResources(
    isSystemInDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val drawableResourcess = if (isSystemInDarkTheme) darkDrawableResource else drawables.current

    CompositionLocalProvider(
        stringResources provides Resources.Strings,
        drawables provides drawableResourcess,
    ) {
        content()
    }
}

