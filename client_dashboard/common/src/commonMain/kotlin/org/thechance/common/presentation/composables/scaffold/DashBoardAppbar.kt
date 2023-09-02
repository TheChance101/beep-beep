package org.thechance.common.presentation.composables.scaffold

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.composables.BpDropdownMenu
import org.thechance.common.presentation.composables.modifier.circleLayout
import org.thechance.common.presentation.composables.modifier.cursorHoverIconHand
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.util.kms

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardAppbar(
    onClickDropDownMenu: () -> Unit,
    onDismissDropDownMenu: () -> Unit,
    title: String,
    username: String,
    firstUsernameLetter: String,
    isDropMenuExpanded: Boolean,
    onLogOut: () -> Unit
) {
    val dropMenuArrowRotateDirection =
        animateFloatAsState(targetValue = if (isDropMenuExpanded) 180f else 0f)

    Column(Modifier.background(Theme.colors.surface)) {
        Row(
            Modifier.height(96.kms).fillMaxWidth()
                .padding(horizontal = 40.kms),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                title,
                style = Theme.typography.headlineLarge,
                color = Theme.colors.contentPrimary
            )
            Box(contentAlignment = Alignment.CenterEnd) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.kms),
                    modifier = Modifier.onClick(onClick = onClickDropDownMenu).cursorHoverIconHand()
                ) {
                    Text(
                        modifier = Modifier.circleLayout().padding(8.kms),
                        style = Theme.typography.titleMedium,
                        text = firstUsernameLetter,
                        color = Color.White
                    )
                    Text(
                        text = username,
                        style = Theme.typography.titleMedium,
                        color = Theme.colors.contentPrimary
                    )
                    Image(
                        painter = painterResource(Resources.Drawable.dropDownArrow),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Theme.colors.contentPrimary),
                        modifier = Modifier.graphicsLayer {
                            rotationZ = dropMenuArrowRotateDirection.value
                        }
                    )
                }
                Box(contentAlignment = Alignment.BottomEnd) {
                    BpDropdownMenu(
                        expanded = isDropMenuExpanded,
                        onDismissRequest = onDismissDropDownMenu,
                        offset = DpOffset.Zero.copy(y = 32.kms),
                        shape = RoundedCornerShape(Theme.radius.medium)
                            .copy(topEnd = CornerSize(Theme.radius.small)),
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                onDismissDropDownMenu()
                                onLogOut()
                            },
                            modifier = Modifier.cursorHoverIconHand(),
                            enabled = true,
                            text = {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.spacedBy(8.kms),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painterResource(Resources.Drawable.logout),
                                        contentDescription = null
                                    )
                                    Text(
                                        text = Resources.Strings.logout,
                                        textAlign = TextAlign.Center,
                                        style = Theme.typography.titleMedium,
                                        color = Theme.colors.primary
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
        Divider(color = Theme.colors.divider, thickness = 1.kms)
    }
}