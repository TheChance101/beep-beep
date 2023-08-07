package com.beepbeep.designSystem

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
@SuppressLint("DiscouragedApi")
@Composable
actual fun fontResources(
    font: String,
    weight: FontWeight,
    style: FontStyle
): Font {

    val context = LocalContext.current
    Log.i("AYA","fontResources: ${context.packageName}")
    val name = font.substringBefore(".")
    val fontRes = context.resources.getIdentifier(name, "font", context.packageName)
    Log.i("AYA","fontResources: ${fontRes}")

    return Font(fontRes,weight,style)
}