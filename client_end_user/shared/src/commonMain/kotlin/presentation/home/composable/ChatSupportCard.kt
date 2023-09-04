package presentation.home.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun ChatSupportCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val animatedFloat by animateFloatAsState(targetValue = if (isPressed) 1.02f else 1f)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(88.dp)
            .scale(animatedFloat)
            .border(
                width = 1.dp,
                color = Theme.colors.divider,
                shape = RoundedCornerShape(Theme.radius.medium)
            )
            .background(Theme.colors.surface)
            .clickable(
                interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = Resources.strings.haveQuestions,
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = Resources.strings.connectWithSupport,
                    style = Theme.typography.title,
                    color = Theme.colors.primary
                )
                Icon(
                    painter = painterResource(Resources.images.arrowRight),
                    contentDescription = Resources.strings.connectWithSupportIconDescription,
                    tint = Theme.colors.primary
                )
            }

        }
        Image(
            painterResource(Resources.images.chatImage),
            contentDescription = Resources.strings.connectWithSupportIconDescription,
            modifier = Modifier.size(56.dp),
        )
    }
}