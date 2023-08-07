package com.beepbeep.designSystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight


@Composable
expect fun fontResources(font: String, weight: FontWeight,
                         style: FontStyle
): Font

