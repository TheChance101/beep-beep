package org.thechance.common.presentation.composable

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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.composable.modifier.circleLayout
import org.thechance.common.presentation.composable.modifier.cursorHoverIconHand
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.util.kms
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainAppbar(
    username: String,
    isDropMenuExpanded: Boolean,
    onClickDropDownMenu: () -> Unit,
    onDismissDropDownMenu: () -> Unit,
    onLogOut: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val arrowRotation = animateFloatAsState(targetValue = if (isDropMenuExpanded) 180f else 0f)
    Column(
        modifier = modifier
            .wrapContentHeight()
            .background(color = Theme.colors.surface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.kms, vertical = 36.kms),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Resources.Drawable.beepBeepLogoExpanded),
                contentDescription = null,
                modifier = Modifier.height(50.kms).align(Alignment.CenterVertically)
            )

            Box(contentAlignment = Alignment.CenterEnd) {
                UsernameWithAvatar(
                    username = username,
                    onClickDropDownMenu = onClickDropDownMenu,
                    arrowRotation = arrowRotation.value
                )
                DropDownMenu(
                    isDropMenuExpanded = isDropMenuExpanded,
                    onDismissDropDownMenu = onDismissDropDownMenu,
                    onLogOut = onLogOut,
                )
            }

        }

        Divider(color = Theme.colors.divider, thickness = 1.kms)
    }

}

@Composable
private fun DropDownMenu(
    isDropMenuExpanded: Boolean,
    onDismissDropDownMenu: () -> Unit,
    onLogOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun UsernameWithAvatar(
    username: String,
    onClickDropDownMenu: () -> Unit,
    arrowRotation: Float,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.kms),
        modifier = Modifier.onClick(onClick = onClickDropDownMenu)
    ) {
        Text(
            text = username.first().toString()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            modifier = Modifier
                .circleLayout()
                .padding(10.kms),
            style = Theme.typography.titleMedium,
            color = Theme.colors.onPrimary
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
            modifier = Modifier.graphicsLayer { rotationZ = arrowRotation }
        )
    }
}