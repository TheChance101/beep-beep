package presentation.main.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun OptionCardItem(
    onClick: () -> Unit,
    title: String,
    imagePath: String,
    color: Color,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Theme.radius.medium),
    textStyle: TextStyle = Theme.typography.titleMedium.copy(color = Theme.colors.contentSecondary),
    imageSize: DpSize = DpSize(104.dp, 104.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val animatedFloat by animateFloatAsState(if (isPressed) 1.08f else 1f)

    Box(
        modifier.widthIn(max = 170.dp).aspectRatio(1.11f).clip(shape).background(color)
            .clickable(interactionSource, null, onClick = onClick)
    ) {
        Text(
            text = title,
            style = textStyle,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = Theme.dimens.space16, start = Theme.dimens.space16)
        )
        Image(
            painter = painterResource(imagePath),
            contentDescription = title,
            modifier = Modifier.size(imageSize).align(Alignment.BottomEnd).scale(animatedFloat)
        )
    }
}