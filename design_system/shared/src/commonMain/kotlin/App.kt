package com.beepbeep.designSystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpChip
import com.beepbeep.designSystem.ui.composable.BpExpandableTextField
import com.beepbeep.designSystem.ui.composable.BpNavigationBar
import com.beepbeep.designSystem.ui.composable.BpNavigationBarItem
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpPriceField
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.composable.BpTap
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.BpTheme
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalResourceApi::class)
@Composable
fun DesignApp() {
    BpTheme {
        Column(
            Modifier.fillMaxSize().background(Theme.colors.background),
//            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            EnabledButtonsPreview()
            DisabledButtonsPreview()
            Spacer(modifier = Modifier.height(16.dp))
//            PreviewTextField()
//            PreviewChips()
            BottomNavigationBarPreview()
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
        BpChip(
            label = "Click me",
            onClick = { isSelected -> selected = isSelected },
            isSelected = selected,
            painter = painterResource("sort.xml")
        )
        BpChip(
            label = "Click me",
            onClick = { isSelected -> selected1 = isSelected },
            isSelected = selected1,
            painter = painterResource("sun.xml")
        )
        BpChip(
            label = "Click me",
            onClick = { isSelected -> selected2 = isSelected },
            isSelected = selected2,
            painter = painterResource("moon_stars.xml")
        )
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PreviewTextField() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
    ) {
        var text1 by rememberSaveable { mutableStateOf("") }
        var text2 by rememberSaveable { mutableStateOf("") }
        var text3 by rememberSaveable { mutableStateOf("") }
        var text4 by rememberSaveable { mutableStateOf("Ahmed Nasser") }
        var text5 by rememberSaveable { mutableStateOf("") }
        var text6 by rememberSaveable { mutableStateOf("") }

        BpSimpleTextField(
            onValueChange = { text5 = it },
            text = text5,
            hint = "Search for users",
            trailingPainter = painterResource("sort.xml")
        )
        BpTextField(
            onValueChange = { text3 = it },
            text = text3,
            label = "Email",
            hint = "Enter your Email",
        )
        BpTextField(
            onValueChange = { text1 = it },
            text = text1,
            label = "Password",
            hint = "Enter your Password",
            keyboardType = KeyboardType.Password
        )
        BpTextField(
            onValueChange = { text2 = it },
            text = text2,
            label = "Username",
            hint = "Enter your Username",
            errorMessage = "incorrect username or password",
        )
        BpTextField(
            onValueChange = { text4 = it },
            text = text4,
            label = "FullName",
            hint = "Enter your FullName",
            correctValidation = true
        )
        BpExpandableTextField(
            onValueChange = { text6 = it },
            text = text6,
            label = "FullName",
            hint = "Enter your FullName",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun EnabledButtonsPreview() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BpButton(
            title = "Button",
            painter = painterResource("sun.xml"),
            enabled = true,
            onClick = { },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        BpOutlinedButton(
            title = "Button",
            enabled = true,
            onClick = { },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        BpTransparentButton(
            title = "Button",
            onClick = { },
            contentColor = Theme.colors.primary
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisabledButtonsPreview() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
    ) {
        BpButton(
            title = "Button",
            enabled = false,
            onClick = { },
            modifier = Modifier.fillMaxWidth().weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        BpOutlinedButton(
            title = "Button",
            enabled = false,
            onClick = {},
            modifier = Modifier.weight(1f).fillMaxWidth()
        )
    }
}


@Composable
fun BottomNavigationBarPreview() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Overview", "Taxis", "Restaurants", "Users")

    BpNavigationBar {
        items.forEachIndexed { index, item ->
            BpNavigationBarItem(
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