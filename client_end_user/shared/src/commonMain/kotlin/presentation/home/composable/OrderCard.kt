package presentation.home.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resources.Resources

@OptIn(ExperimentalResourceApi::class)
@Composable
fun OrderCard(
    onClick: () -> Unit,
    painter: Painter,
    buttonTitle: String,
    modifier: Modifier = Modifier,
    color: Color = Theme.colors.pink,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val animatedFloat by animateFloatAsState(targetValue = if (isPressed) 1.02f else 1f)
    Column(
        modifier = modifier
            .height( height = 140.dp)
            .clip(shape = RoundedCornerShape(Theme.radius.medium))
            .background(color)
            .clickable(
                interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        Box {
            Image(
                painter = painter,
                contentDescription = Resources.strings.orderCardImageDescription,
                modifier = Modifier.size(width = 120.dp, height = 100.dp).scale(animatedFloat),
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 8.dp)
                .size(width = 114.dp, height = 32.dp)
                .clip(shape = RoundedCornerShape(Theme.radius.medium))
                .background(Theme.colors.secondary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 4.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            Text(
                text = buttonTitle,
                style = Theme.typography.title,
                color = Theme.colors.primary
            )
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(Resources.images.arrowRight),
                contentDescription = Resources.strings.orderCardImageDescription,
                tint = Theme.colors.primary
            )
        }
    }

}