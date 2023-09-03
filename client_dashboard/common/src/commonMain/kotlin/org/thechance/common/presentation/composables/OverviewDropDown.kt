package org.thechance.common.presentation.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.composables.modifier.cursorHoverIconHand
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.util.kms

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OverviewDropDown(
    onDropDownMenuClicked: () -> Unit,
    onDismissDropDownMenu: () -> Unit,
    onMenuItemClicked: (Int) -> Unit,
    isExpanded: Boolean,
    items: List<String>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
) {

    val dropMenuArrowRotateDirection = animateFloatAsState(targetValue = if (isExpanded) 180f else 0f)

    Box(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.kms),
            modifier = Modifier.onClick(onClick = onDropDownMenuClicked).cursorHoverIconHand()
        ) {
            Text(
                text = items[selectedIndex],
                style = Theme.typography.body,
                color = Theme.colors.contentSecondary,
                modifier = Modifier
            )
            Image(painter = painterResource(Resources.Drawable.dropDownArrow),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Theme.colors.contentPrimary),
                modifier = Modifier.graphicsLayer {
                    rotationZ = dropMenuArrowRotateDirection.value
                }
            )
        }

        Box {
            BpDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = onDismissDropDownMenu,
                offset = DpOffset.Zero.copy(y = 32.kms),
                shape = RoundedCornerShape(Theme.radius.medium).copy(topEnd = CornerSize(Theme.radius.small)),
            ) {
                items.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        onClick = { onMenuItemClicked(index) },
                        modifier = Modifier.cursorHoverIconHand(),
                        enabled = true,
                        text = {
                            Text(
                                text = text,
                                textAlign = TextAlign.Center,
                                style = Theme.typography.body,
                                color = Theme.colors.contentSecondary
                            )
                        }
                    )
                }
            }
        }
    }
}