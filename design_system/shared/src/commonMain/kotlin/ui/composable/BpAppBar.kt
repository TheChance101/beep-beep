package com.beepbeep.designSystem.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import com.beepbeep.designSystem.ui.theme.Theme.colors
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun BpAppBar(
    onNavigateUp: () -> Unit,
    title:String ="",
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = Theme.dimens.space8) ,
                text = title,
                style =  Theme.typography.titleLarge,
                color = colors.contentPrimary,
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource("arrow_left.xml"),
                contentDescription = "",
                modifier = Modifier.clickable { onNavigateUp() },
                tint = colors.contentSecondary
            )
        },
        actions = {
            content()
        },
        colors= TopAppBarDefaults.smallTopAppBarColors(containerColor = colors.surface),
        modifier = modifier.padding(horizontal = 16.dp),
    )
}

