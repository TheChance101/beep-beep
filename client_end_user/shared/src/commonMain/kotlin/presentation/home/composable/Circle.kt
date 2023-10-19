package presentation.home.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun Circle(
    modifier: Modifier = Modifier,
    circleSize: Dp = 4.dp,
    circleColor: Color = Theme.colors.disable,
) {
    Spacer(modifier.size(circleSize).drawBehind { drawCircle(circleColor) })
}
