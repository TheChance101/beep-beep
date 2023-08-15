package org.thechance.common.ui.scaffold

import org.thechance.common.ui.composables.DropdownMenuNoPaddingVertical
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.onClick
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import org.thechance.common.ui.composables.modifier.border
import org.thechance.common.ui.composables.modifier.circleLayout
import org.thechance.common.ui.composables.modifier.cursorHoverIconHand

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardAppbar(title: String, username: String, onLogOut: () -> Unit) {
    val isDropMenuExpanded = remember { mutableStateOf(false) }
    val dropMenuArrowRotateDirection =
        animateFloatAsState(targetValue = if (isDropMenuExpanded.value) 180f else 0f)

    Row(
        Modifier.height(96.dp).fillMaxWidth().background(Theme.colors.surface)
            .border(bottom = BorderStroke(width = 1.dp, color = Theme.colors.divider))
            .padding(horizontal = 40.dp),
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
                modifier = Modifier.onClick {
                    isDropMenuExpanded.value = !isDropMenuExpanded.value
                }.cursorHoverIconHand()
            ) {
                Text(
                    modifier = Modifier.circleLayout().padding(8.dp),
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
                DropdownMenuNoPaddingVertical(
                    expanded = isDropMenuExpanded.value,
                    onDismissRequest = { isDropMenuExpanded.value = false },
                    offset = DpOffset(0.dp, 24.dp),
                    modifier = Modifier

                ) {
                    DropdownMenuItem(
                        onClick = {
                            isDropMenuExpanded.value = false
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
}