package com.beepbeep.designSystem.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color


// Will Replaced by Mustafa Colors
private val LightColors = lightColors(
    primary = Color(0xFFD0BCFF),
    secondary = Color(0xFF10BCFF),
    background = Color(0x99F0BCFF),
)

// Will Replaced by Mustafa Colors
private val DarkColors = darkColors(
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
    content: @Composable () -> Unit
) {
    val colorScheme = if (!darkTheme) {
        LightColors
    } else {
        DarkColors
    }

    CompositionLocalProvider(localDimens provides Dimens()) {
        MaterialTheme(
            colors = colorScheme,
            shapes = shapes,
            // typography of Aya Will be added here
            content = content
        )
    }
}