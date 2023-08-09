package com.beepbeep.designSystem.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Colors
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.colorScheme
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.shapes
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.typography
import com.beepbeep.designSystem.ui.theme.LocalDimens
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.dimens

@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Int = 56,
    icon: @Composable (() -> Unit)
){
    Surface(
        modifier=  modifier
        .height(height.dp)
        .border(width = 1.dp, color =  colorScheme.outline, shape = shapes.medium)
        .padding(horizontal = dimens.space16, vertical = dimens.space10)
            .clickable(
                    indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() },
        color = Color.Transparent,
      ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(dimens.space8),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            icon()
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = typography.bodyLarge,
                color = colorScheme.onPrimary
            )
        }
    }
}