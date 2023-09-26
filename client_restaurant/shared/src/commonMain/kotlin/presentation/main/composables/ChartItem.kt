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
import androidx.compose.foundation.layout.widthIn
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
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Theme.radius.medium),
    content: @Composable () -> Unit
) {
    Column(
        modifier.widthIn(max = 328.dp).clip(shape).background(Theme.colors.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {

        Column(
            Modifier.fillMaxWidth(),

            ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Column() {
                    Image(painter = imagePainter, contentDescription = null, Modifier.size(24.dp))
                    Text(
                        text = title,
                        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$4700",
                        style = Theme.typography.title.copy(color = Theme.colors.contentPrimary)
                    )
                    Text(
                        text = Resources.strings.thisWeek,
                        style = Theme.typography.caption.copy(color = Theme.colors.contentTertiary)
                    )
                }
            }
            Box(Modifier.fillMaxWidth().aspectRatio(1f)) {
                content()
            }
        }
    }
}
