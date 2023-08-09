package com.beepbeep.designSystem.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BeepBeepLogo() {
    Image(painter = painterResource("ic_beep_beep_logo.xml"), contentDescription = null)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BeepBeepIcon() {
    Image(painter = painterResource("ic_beep_beep_icon.xml"), contentDescription = null)
}