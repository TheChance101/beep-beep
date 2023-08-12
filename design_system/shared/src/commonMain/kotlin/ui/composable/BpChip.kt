package com.beepbeep.designSystem.ui.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BpChip(
    label: String,
    isSelected: Boolean,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    painter: Painter? = null
) {
    val containerColor = animateColorAsState(
        targetValue = if (isSelected) Theme.colors.primary else Color.Transparent
    )
    val labelColor = animateColorAsState(
        targetValue = if (isSelected) Theme.colors.onPrimary
        else Theme.colors.contentSecondary
    )
    val iconColor = animateColorAsState(
        targetValue = if (isSelected) Theme.colors.onPrimary
        else Theme.colors.contentSecondary
    )
    AssistChip(
        modifier = modifier.height(32.dp),
        onClick = { onClick(!isSelected) },
        label = { Text(text = label, style = Theme.typography.title) },
        leadingIcon = {
            painter?.let {
                Icon(
                    painter = painter,
                    contentDescription = "$label icon",
                    Modifier.size(AssistChipDefaults.IconSize),
                    tint = iconColor.value
                )
            }
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = containerColor.value,
            labelColor = labelColor.value
        ),
        border = AssistChipDefaults.assistChipBorder(
            borderColor = Theme.colors.contentBorder,
            borderWidth = 1.dp
        ),
        shape = Theme.radius.small
    )
}