package presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme


@Composable
fun BpEmptyScreen(
    painter: Painter, text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = Theme.typography.body,
    isVisible: Boolean = false
) {
    if (isVisible) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
                Image(
                    painter = painter,
                    contentDescription = "",
                    modifier = Modifier.size(120.dp)
                )
                Text(text, style = style, color = style.color.copy(alpha = 0.38f))
            }
        }
    }
}