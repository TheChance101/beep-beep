package presentation.composable.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun Modifier.roundedBorderShape(
    borderWidth: Dp = 1.dp,
    borderColor: Color = Theme.colors.divider,
    shape: Shape = RoundedCornerShape(Theme.radius.medium),
    background: Color = Theme.colors.surface
) = then(
    Modifier.border(
        width = borderWidth,
        color = borderColor,
        shape = shape
    ).clip(shape = shape).background(background)
)