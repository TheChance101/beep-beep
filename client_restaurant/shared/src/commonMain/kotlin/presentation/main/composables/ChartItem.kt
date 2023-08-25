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
import presentation.main.ChartItemUiState
import resources.Resources

@Composable
fun ChartItem(
    imagePainter: Painter,
    sign: Char? = null,
    chartItemUiState: ChartItemUiState,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Theme.radius.medium),
) {
    Column(
        modifier.widthIn(max = 328.dp).clip(shape).background(Theme.colors.surface)
            .padding(Theme.dimens.space16),
        verticalArrangement = Arrangement.spacedBy(Theme.dimens.space24),
    ) {
        with(chartItemUiState) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(painter = imagePainter, contentDescription = null, Modifier.size(24.dp))
                    Text(
                        text = title,
                        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(Theme.dimens.space4),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "${sign ?: ""}$totalThisWeek",
                        style = Theme.typography.title.copy(color = Theme.colors.contentPrimary)
                    )
                    Text(
                        text = Resources.strings.thisWeek,
                        style = Theme.typography.caption.copy(color = Theme.colors.contentTertiary)
                    )
                }
            }
            Box(Modifier.fillMaxWidth().aspectRatio(1.58f).background(Theme.colors.secondary))
        }
    }
}
