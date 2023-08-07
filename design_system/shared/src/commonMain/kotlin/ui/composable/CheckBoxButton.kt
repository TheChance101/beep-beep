package com.beepbeep.designSystem.ui.composable

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

@Composable
fun CheckBoxButton(
    text: String = "",
    isChecked: Boolean =false,
    onCheck: (Boolean) -> Unit,
){

    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = isChecked,
            interactionSource = remember { MutableInteractionSource() },
            colors=   CheckboxDefaults.colors(
                checkmarkColor =  MaterialTheme.colorScheme.background,
                checkedColor = MaterialTheme.colorScheme.primary,
            ),
            onCheckedChange = { onCheck(it) },
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}