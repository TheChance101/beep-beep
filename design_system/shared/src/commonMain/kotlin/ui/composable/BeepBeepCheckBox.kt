package com.beepbeep.designSystem.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.colorScheme
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.dimens
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.shapes
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.typography
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)
@Composable
fun BeepBeepCheckBox(
    modifier : Modifier = Modifier,
    text: String = "",
    isChecked: Boolean =false,
    enabled: Boolean = true,
    onCheck: (Boolean) -> Unit,
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimens.space8),
        verticalAlignment = Alignment.CenterVertically) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            Checkbox(
                modifier = Modifier.size(dimens.size32).padding(0.dp),
                enabled = enabled,
                checked = isChecked,
                interactionSource = remember { MutableInteractionSource() },
                colors = CheckboxDefaults.colors(
                    checkmarkColor = colorScheme.background,
                    checkedColor = colorScheme.primary,
                    uncheckedColor = colorScheme.outline
                ),
                onCheckedChange = { onCheck(it) },
            )
        }
        Text(text = text, style = typography.body,color = colorScheme.onSecondary)
    }
}
