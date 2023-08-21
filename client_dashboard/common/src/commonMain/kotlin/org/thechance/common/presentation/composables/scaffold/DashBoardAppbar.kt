package org.thechance.common.presentation.composables.scaffold

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.composables.BpDropdownMenu
import org.thechance.common.presentation.composables.modifier.circleLayout
import org.thechance.common.presentation.composables.modifier.cursorHoverIconHand
import org.thechance.common.presentation.util.kms

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardAppbar(
    onClickDropDownMenu: () -> Unit,
    onDismissDropDownMenu: () -> Unit,
    title: String,
    username: String,
    isDropMenuExpanded: Boolean,
    onLogOut: () -> Unit
) {
    val dropMenuArrowRotateDirection =
        animateFloatAsState(targetValue = if (isDropMenuExpanded) 180f else 0f)

    Column(Modifier.background(Theme.colors.surface)) {
        Row(
            Modifier.height(96.kms).fillMaxWidth()
                .padding(horizontal = Theme.dimens.space40),
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
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.onClick(onClick = onClickDropDownMenu).cursorHoverIconHand()
                ) {
                    Text(
                        modifier = Modifier.circleLayout().padding(Theme.dimens.space8),
                        style = Theme.typography.titleMedium,
                        text = username.first().uppercase(),
                        color = Color.White
                    )
                    Text(
                        username,
                        style = Theme.typography.titleMedium,
                        color = Theme.colors.contentPrimary
                    )
                    Image(
                        painter = painterResource("ic_drop_down_arrow.svg"),
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
                        offset = DpOffset.Zero.copy(y = Theme.dimens.space24),
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
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painterResource("ic_logout.svg"),
                                        contentDescription = null
                                    )
                                    Text(
                                        text = "Logout",
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