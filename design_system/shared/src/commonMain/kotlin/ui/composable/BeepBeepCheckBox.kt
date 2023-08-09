package com.beepbeep.designSystem.ui.composable

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.colorScheme
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.dimens
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.typography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BeepBeepCheckBox(
    modifier: Modifier = Modifier,
    text: String = "",
    isChecked: Boolean = false,
    onCheck: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimens.space16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            Checkbox(
                checked = isChecked,
                interactionSource = remember { MutableInteractionSource() },
                colors = CheckboxDefaults.colors(
                    checkmarkColor = colorScheme.background,
                    checkedColor = colorScheme.primary,
                    uncheckedColor = colorScheme.onPrimary
                ),
                onCheckedChange = { onCheck(it) },
            )
        }
        Text(text = text, style = typography.bodyLarge, color = colorScheme.onPrimary)
    }
}