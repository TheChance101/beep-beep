package com.beepbeep.designSystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BeepBeepTextField
import com.beepbeep.designSystem.ui.composable.CheckBoxButton
import com.beepbeep.designSystem.ui.composable.OutlinedButton
import com.beepbeep.designSystem.ui.composable.PrimaryButton
import com.beepbeep.designSystem.ui.composable.SwitchButton
import com.beepbeep.designSystem.ui.composable.TextButton
import com.beepbeep.designSystem.ui.composable.ToggleButton
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@Composable
fun DesignApp() {
    BeepBeepTheme {
        Column(
            Modifier.fillMaxSize().background(BeepBeepTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButtonPrev()
            CheckBoxButtonPrev()
            TogglePreview()
            SwitchPreview()
            PreviewEnabledButtons()
//            PreviewDisabledButtons()
            PreviewTextField()
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
            Text("Button",   fontFamily = FontFamily(fontResources("borel_regular.ttf","borel_regular")),)
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

@Composable
fun PreviewTextField() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
    ) {
        var text1 by rememberSaveable { mutableStateOf("") }
        var text2 by rememberSaveable { mutableStateOf("") }
        var text3 by rememberSaveable { mutableStateOf("") }

        BeepBeepTextField(
            onValueChange = { text1 = it },
            text = text1,
            label = "Password",
            keyboardType = KeyboardType.Password
        )
        BeepBeepTextField(
            onValueChange = { text2 = it },
            text = text2,
            label = "username",
            errorMessage = "incorrect username or password" ,
        )
    }
}

@Composable
fun SwitchPreview() {
    var isToggle by remember { mutableStateOf(false) }
    SwitchButton(selected = isToggle, onUpdate = { isToggle = it })
}

@Composable
fun TogglePreview() {
    var isToggle by remember { mutableStateOf(false) }
    ToggleButton(isToggle, onToggle = { isToggle = !isToggle })
}

@Composable
fun  CheckBoxButtonPrev(){
    var isChecked by remember { mutableStateOf(false) }
    CheckBoxButton(
        text = "CheckBox",
        isChecked = isChecked,
        onCheck = {
            isChecked = it
        },
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TextButtonPrev(){
    TextButton(
        text = "TextButton",
        onClick = { },
    ){
        Icon(
            painter = painterResource("sort.xml"),
            contentDescription = "",
            tint =  MaterialTheme.colorScheme.onPrimary,
        )
    }
}
expect fun getPlatformName(): String