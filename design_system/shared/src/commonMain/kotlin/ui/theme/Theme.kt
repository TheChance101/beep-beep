package com.beepbeep.designSystem.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf

private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    secondary = secondaryLight,
    onPrimary = contentPrimaryLight,
    onSecondary = contentSecondaryLight,
    onTertiary = contentTertiaryLight,
    outline = contentBorderLight,
    surface = surfaceLight,
    onPrimaryContainer = onPrimaryLight,
    tertiary = hoverLight,
    background = backgroundLight,
    tertiaryContainer = disableLight,
    onSurface = successLight,
    inversePrimary = successContainerLight,
    error = warningLight,
    errorContainer = warningContainerLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    secondary = secondaryDark,
    onPrimary = contentPrimaryDark,
    onSecondary = contentSecondaryDark,
    onTertiary = contentTertiaryDark,
    outline = contentBorderDark,
    surface = surfaceDark,
    onPrimaryContainer = onPrimaryDark,
    tertiary = hoverDark,
    background = backgroundDark,
    tertiaryContainer = disableDark,
    onSurface = successDark,
    inversePrimary = successContainerDark,
    error = warningDark,
    errorContainer = warningContainerDark,
)

private val LocalDimens = compositionLocalOf { Dimens() }
private val LocalColorScheme = staticCompositionLocalOf { lightColorScheme() }
private val LocalShapes = staticCompositionLocalOf { shapes }


@Composable
fun BeepBeepTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(LocalDimens provides Dimens(), LocalColorScheme provides colorScheme, LocalShapes provides shapes) {
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = shapes,
            // typography of Aya Will be added here
            content = content
        )
    }
}

object BeepBeepTheme {
    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

//    val typography: Typography
//        @Composable
//        @ReadOnlyComposable
//        get() = LocalTypography.current
//
    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val dimens: Dimens
        @Composable
        @ReadOnlyComposable
        get() = LocalDimens.current
}