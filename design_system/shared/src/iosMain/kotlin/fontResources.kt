package com.beepbeep.designSystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource


//private val cache: MutableMap<String, Font> = mutableMapOf()
//
//@Composable
//
//actual fun fontResources(
//    font: String,
//    weight: FontWeight,
//): Font {
//    return cache.getOrPut(font) {
//        val byteArray = runBlocking {
//            resource("font/$font.ttf").readBytes()
//        }
//        Font(font, byteArray, weight)
//    }
//}