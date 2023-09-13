package org.thechance.common.presentation.resources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val stringResources = staticCompositionLocalOf<StringResources> {
    throw Exception("string resources is not provided make sure you are using ProvideResources")
}

private val drawables = staticCompositionLocalOf<DrawableResources> {
    throw Exception("drawables resources is not provided make sure you are using ProvideResources")
}

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
    val drawableResources = if (isSystemInDarkTheme) darkDrawableResource else lightDrawableResource

    CompositionLocalProvider(
        stringResources provides englishStrings,
        drawables provides drawableResources,
    ) {
        content()
    }
}