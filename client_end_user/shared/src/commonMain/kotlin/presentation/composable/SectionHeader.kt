package presentation.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resources.Resources

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SectionHeader(
    title: String,
    onClickViewAll: () -> Unit = { },
    modifier: Modifier = Modifier,
    showViewAll: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val animatedFloat by animateFloatAsState(targetValue = if (isPressed) 1.2f else 1f)
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = Theme.typography.titleLarge, color = Theme.colors.contentPrimary)

        if (showViewAll) {
            Row(
                Modifier.clickable(interactionSource, indication = null, onClick = onClickViewAll)
                    .scale(animatedFloat),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Resources.strings.viewAll,
                    style = Theme.typography.caption,
                    color = Theme.colors.primary
                )
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(Resources.images.arrowRight),
                    contentDescription = Resources.strings.seeAllDescription,
                    tint = Theme.colors.primary
                )
            }
        }

    }
}