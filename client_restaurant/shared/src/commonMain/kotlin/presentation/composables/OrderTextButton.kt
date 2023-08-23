package presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme

@Composable
fun OrderTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = Theme.colors.primary,
    shape: RoundedCornerShape = RoundedCornerShape(Theme.radius.small),
    height: Dp = 32.dp,
    border: BorderStroke  = BorderStroke(width = 0.dp , color = Theme.colors.contentTertiary)
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.height(height),
        shape = shape,
    ) {
        Text(text = text, style = Theme.typography.title, color = textColor)
    }
}