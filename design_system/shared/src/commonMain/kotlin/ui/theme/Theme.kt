package com.beepbeep.designSystem.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val localDimens = staticCompositionLocalOf { Dimens() }
private val localColorScheme = staticCompositionLocalOf { LightBPColors }
private val localRadius = staticCompositionLocalOf { Radius() }
private val localTypography = staticCompositionLocalOf { Typographies() }

@Composable
fun BpTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val colorScheme = if (useDarkTheme) DarkBpColors else LightBPColors

    val typography = Typographies(
        headlineLarge = headlineLarge(),
        headline = headline(),
        titleLarge = titleLarge(),
        title = title(),
        bodyLarge = bodyLarge(),
        body = body(),
        caption = caption(),
    )

    CompositionLocalProvider(
        localColorScheme provides colorScheme,
        localTypography provides typography,
        localDimens provides Dimens(),
        localRadius provides Radius(),
    ) {
        content()
    }
}

object Theme {
    val colors: BpColors
        @Composable
        @ReadOnlyComposable
        get() = localColorScheme.current

    val typography: Typographies
        @Composable
        @ReadOnlyComposable
        get() = localTypography.current

    val radius: Radius
        @Composable
        @ReadOnlyComposable
        get() = localRadius.current

    val dimens: Dimens
        @Composable
        @ReadOnlyComposable
        get() = localDimens.current
}