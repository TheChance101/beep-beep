package presentation.main.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import resources.Resources

@Composable
fun ChartItem(
    imagePainter: Painter,
    title: String,
    totalPrice: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Theme.radius.medium),
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Theme.colors.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = imagePainter,
                    contentDescription = "",
                    Modifier.size(24.dp)
                )
                Text(
                    text = title,
                    style = Theme.typography.titleMedium,
                    color = Theme.colors.contentPrimary
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = totalPrice,
                    style = Theme.typography.title,
                    color = Theme.colors.contentPrimary
                )
                Text(
                    text = Resources.strings.thisWeek,
                    style = Theme.typography.caption,
                    color = Theme.colors.contentTertiary
                )
            }
        }
        Box(Modifier.fillMaxWidth()) {
            content()
        }
    }

}
