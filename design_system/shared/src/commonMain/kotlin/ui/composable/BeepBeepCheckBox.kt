package com.beepbeep.designSystem.ui.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.colorScheme
import com.beepbeep.designSystem.ui.theme.BeepBeepTheme.dimens
import com.beepbeep.designSystem.ui.theme.shapes

@Composable
fun BeepBeepCheckBox(
    label: String,
    onCheck: () -> Unit,
    modifier: Modifier = Modifier,
    size : Int = 32,
    isChecked: Boolean = false,
    gapBetweenLabelAndCheckbox: Int = 8,
) {
    val checkboxColor: Color by animateColorAsState(
        targetValue = if (isChecked) colorScheme.primary else Color.Transparent,
        animationSpec = tween(300)
    )
    val density = LocalDensity.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(gapBetweenLabelAndCheckbox.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .toggleable(
                    value = isChecked,
                    role = Role.Checkbox,
                    onValueChange = {
                        onCheck()
                    }
                ),
            elevation = 0.dp,
            shape = shapes.small,
            border = BorderStroke(1.dp, color = if (isChecked) colorScheme.primary else colorScheme.outline),
        ) {
            Box(modifier = Modifier.size(size.dp).background(color = checkboxColor, shape = shapes.small),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = isChecked,
                    enter = slideInHorizontally(
                        animationSpec = tween(200)
                    ) {
                        with(density) { (24f * -0.5).dp.roundToPx() }
                    } + expandHorizontally(
                        expandFrom = Alignment.Start,
                        animationSpec = tween(200)
                    ),
                    exit = fadeOut()
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = White,
                    )
                }
            }
        }
        Text(text = label, style = BeepBeepTheme.typography.body, color = colorScheme.onSecondary)
    }
}