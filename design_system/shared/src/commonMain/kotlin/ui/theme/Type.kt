package com.beepbeep.designSystem.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.beepbeep.designSystem.fontResources

@Composable
fun Typography(): Typography {
    val dimens=LocalDimens.current

 return   Typography(
    headlineLarge = TextStyle(
        fontSize = dimens.fontSize24,
        lineHeight =dimens.lineHeight32,
        fontFamily =FontFamily(fontResources("font/Borel-medium.ttf",FontWeight.W600)),
        fontWeight = FontWeight.W600,
        color = Color.Black,
    ),
    headlineMedium = TextStyle(
        fontSize = dimens.fontSize20,
        fontFamily =FontFamily(fontResources("font/roboto-medium.ttf",FontWeight.W600)),
        fontWeight = FontWeight.W600,
        color = Color.Black,
    ),
    titleLarge = TextStyle(
        fontFamily =FontFamily(fontResources("font/roboto-medium.ttf",FontWeight.W600)),
        fontSize = dimens.fontSize16,
        lineHeight = dimens.lineHeight20,
        fontWeight = FontWeight.W600,
        color = Color.Black,

        ),
    titleMedium = TextStyle(
        fontSize = dimens.fontSize14,
        fontFamily =FontFamily(fontResources("font/roboto-medium.ttf",FontWeight.W600)),
        fontWeight = FontWeight.W600,
        color = Color.Black,
    ),
    bodyLarge = TextStyle(
        fontFamily =FontFamily(fontResources("font/roboto-Regular.ttf",FontWeight.W400)),
        fontWeight = FontWeight.W400,
        fontSize =  dimens.fontSize16,
        color = Color.Black,
    ),
    bodyMedium = TextStyle(
        fontSize = dimens.fontSize14,
        lineHeight = dimens.lineHeight19,
        fontFamily =FontFamily(fontResources("font/roboto-Regular.ttf",FontWeight.W400)),
        fontWeight = FontWeight.W400,
        color = Color.Black,
    ),
    labelMedium = TextStyle(
        fontSize =dimens.fontSize12,
        fontFamily =FontFamily(fontResources("font/roboto-Regular.ttf",FontWeight.W400)),
        fontWeight = FontWeight.W400,
        color = Color.Black,
    ),

)}