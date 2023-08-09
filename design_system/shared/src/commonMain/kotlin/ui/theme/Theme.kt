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

private val lightColorScheme = lightColorScheme(
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
    surfaceTint = surfaceTintLight,
)

private val darkColorScheme = darkColorScheme(
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
    surfaceTint = surfaceTintDark,
)

val LocalDimens = staticCompositionLocalOf { Dimens() }
val LocalColorScheme = staticCompositionLocalOf { lightColorScheme }
val LocalShapes = staticCompositionLocalOf { shapes }
val LocalTypography = staticCompositionLocalOf { Typographies() }

@Composable
fun BeepBeepTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val colorScheme = if (useDarkTheme) darkColorScheme else lightColorScheme
    val typography = Typographies(
        headlineLarge = Typography().headlineLarge,
        headline = Typography().headlineMedium,
        titleLarge = Typography().titleLarge,
        title = Typography().titleMedium,
        bodyLarge = Typography().bodyLarge,
        body = Typography().bodyMedium,
        caption = Typography().labelMedium,
    )

    CompositionLocalProvider(
        LocalDimens provides Dimens(),
        LocalColorScheme provides colorScheme,
        LocalShapes provides shapes,
        LocalTypography provides typography,
        content = content
    )
}

object BeepBeepTheme {
    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    val typography: Typographies
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val dimens: Dimens
        @Composable
        @ReadOnlyComposable
        get() = LocalDimens.current
}