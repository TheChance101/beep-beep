package com.beepbeep.designSystem.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColors = lightColors(
    primary = Color(0xFFD0BCFF),
    secondary = Color(0xFF10BCFF),
    background = Color(0x99F0BCFF),
)


private val DarkColors = darkColors(
    primary = Color(0xFFD4BCFF),
    secondary = Color(0xF7D0BCFF),
    background = Color(0x9FD0BCFF),
)

@Composable
fun BeepBeepTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}