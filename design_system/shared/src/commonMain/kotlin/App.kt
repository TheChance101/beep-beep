package com.beepbeep.designSystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BeepBeepButton
import com.beepbeep.designSystem.ui.composable.BeepBeepChip
import com.beepbeep.designSystem.ui.composable.BeepBeepNavigationBar
import com.beepbeep.designSystem.ui.composable.BeepBeepNavigationBarItem
import com.beepbeep.designSystem.ui.composable.BeepBeepTextField
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@Composable
fun DesignApp() {
    BeepBeepTheme {
        Column(
            Modifier.fillMaxSize().background(Theme.colors.background),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Bottom
        ) {
//            EnabledButtonsPreview()
//            DisabledButtonsPreview()
//            Spacer(modifier = Modifier.height(16.dp))
            PreviewTextField()
            PreviewChips()
//            BottomNavigationBarPreview()
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PreviewChips() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        var selected by remember { mutableStateOf(false) }
        var selected1 by remember { mutableStateOf(true) }
        var selected2 by remember { mutableStateOf(true) }
        BeepBeepChip(
            label = "Click me",
            onClick = { isSelected -> selected = isSelected },
            isSelected = selected,
            painter = painterResource("sort.xml")
        )
        BeepBeepChip(
            label = "Click me",
            onClick = { isSelected -> selected1 = isSelected },
            isSelected = selected1,
            painter = painterResource("sun.xml")
        )
        BeepBeepChip(
            label = "Click me",
            onClick = { isSelected -> selected2 = isSelected },
            isSelected = selected2,
            painter = painterResource("moon_stars.xml")
        )
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
        var text4 by rememberSaveable { mutableStateOf("Ahmed Nasser") }

        BeepBeepTextField(
            onValueChange = { text3 = it },
            text = text3,
            label = "Email",
        )
        BeepBeepTextField(
            onValueChange = { text1 = it },
            text = text1,
            label = "Password",
            keyboardType = KeyboardType.Password
        )
        BeepBeepTextField(
            onValueChange = { text2 = it },
            text = text2,
            label = "Username",
            errorMessage = "incorrect username or password",
        )
        BeepBeepTextField(
            onValueChange = { text4 = it },
            text = text4,
            label = "FullName",
            correctValidation = true
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnabledButtonsPreview() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
    ) {
        BeepBeepButton(
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
fun DisabledButtonsPreview() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
    ) {
        BeepBeepButton(
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
fun BottomNavigationBarPreview() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Overview", "Taxis", "Restaurants", "Users")

    BeepBeepNavigationBar {
        items.forEachIndexed { index, item ->
            BeepBeepNavigationBarItem(
                icon = { tint ->
                    Icon(Icons.Filled.Favorite, contentDescription = item, tint = tint)
                },
                label = { style ->
                    Text(item, style = style)
                },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}



expect fun getPlatformName(): String