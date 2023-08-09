package com.beepbeep.designSystem.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.beepbeep.designSystem.fontResources
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.colorScheme
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.dimens

@Composable
fun Typography(): Typography {

    return Typography(
        headlineLarge = TextStyle(
            fontSize = dimens.fontSize24,
            lineHeight = dimens.lineHeight32,
            fontFamily = FontFamily(fontResources("roboto_medium.ttf", "roboto_medium")),
            fontWeight = FontWeight.W600,
        ),
        headlineMedium = TextStyle(
            fontSize = dimens.fontSize20,
            fontFamily = FontFamily(fontResources("roboto_medium.ttf", "roboto_medium")),
            fontWeight = FontWeight.W600,
        ),
        titleLarge = TextStyle(
            fontFamily = FontFamily(fontResources("roboto_medium.ttf", "roboto_medium")),
            fontSize = dimens.fontSize16,
            lineHeight = dimens.lineHeight20,
            fontWeight = FontWeight.W600,
        ),
        titleMedium = TextStyle(
            fontSize = dimens.fontSize14,
            fontFamily = FontFamily(fontResources("roboto_medium.ttf", "roboto_medium")),
            fontWeight = FontWeight.W600,
        ),
        bodyLarge = TextStyle(
            fontFamily = FontFamily(fontResources("roboto_regular.ttf", "roboto_medium")),
            fontWeight = FontWeight.W400,
            fontSize = dimens.fontSize16,
        ),
        bodyMedium = TextStyle(
            fontSize = dimens.fontSize14,
            lineHeight = dimens.lineHeight19,
            fontFamily = FontFamily(fontResources("roboto_regular.ttf", "roboto_regular")),
            fontWeight = FontWeight.W400,
        ),
        labelMedium = TextStyle(
            fontSize = dimens.fontSize12,
            fontFamily = FontFamily(fontResources("roboto_regular.ttf", "roboto_regular")),
            fontWeight = FontWeight.W400,
        ),
        )
}
