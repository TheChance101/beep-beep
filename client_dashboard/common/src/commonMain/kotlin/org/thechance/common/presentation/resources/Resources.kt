package org.thechance.common.presentation.resources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.intl.Locale
import kotlinx.datetime.toLocalDate

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
    val localLanguage  = Locale.current.toLanguageTag().split("-")[1].lowercase()
    val drawableResources = if (isSystemInDarkTheme) darkDrawableResource else lightDrawableResource
    CompositionLocalProvider(
            stringResources provides LocalizationManager.getStringResources(languageCode = localLanguage),
            LocalLayoutDirection provides LocalizationManager.getLayoutDirection(languageCode=localLanguage),
            drawables provides drawableResources,
    ) {
        content()
    }
}

