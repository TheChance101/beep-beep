package com.beepbeep.designSystem.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


// Will Replaced by Mustafa Colors
private val LightColors = darkColorScheme(
    primary = Color(0xFFD0BCFF),
    secondary = Color(0xFF10BCFF),
    background = Color(0x99F0BCFF),
    onPrimary = Color(0x99F0BCFF),
    onSecondary = Color(0x99F0BCFF),
)

// Will Replaced by Mustafa Colors
private val DarkColors = lightColorScheme(
    primary = Color(0xFFD4BCFF),
    secondary = Color(0xF7D0BCFF),
    background = Color(0x9FD0BCFF),
)

private val localDimens = compositionLocalOf { Dimens() }

val dimens: Dimens
    @Composable
    @ReadOnlyComposable
    get() = localDimens.current


@Composable
fun BeepBeepTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (!darkTheme) {
        LightColors
    } else {
        DarkColors
    }
    CompositionLocalProvider(localDimens provides Dimens()) {
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = shapes,
            // typography of Aya Will be added here
            typography= Typography(),
            content = content
        )
    }
}