package com.beepbeep.designSystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.OutlinedButton
import com.beepbeep.designSystem.ui.composable.PrimaryButton
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme


@Composable
fun DesignApp() {
    BeepBeepTheme {
        Column(
            Modifier.fillMaxSize().background(BeepBeepTheme.colorScheme.background),
            verticalArrangement = Arrangement.Bottom
        ) {
            PreviewEnabledButtons()
            PreviewDisabledButtons()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewEnabledButtons() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
    ) {
        PrimaryButton(
            enabled = true,
            onClick = { },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("Button")
        }

        OutlinedButton(
            enabled = true,
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Button")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewDisabledButtons() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
    ) {
        PrimaryButton(
            enabled = false,
            onClick = { },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("Button")
        }

        OutlinedButton(
            enabled = false,
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Button")
        }
    }
}

expect fun getPlatformName(): String