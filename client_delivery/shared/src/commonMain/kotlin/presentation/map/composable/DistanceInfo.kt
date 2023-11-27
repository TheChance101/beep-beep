package presentation.map.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resources.Resources

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DistanceInfo(orderDistance: String, orderDuration: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally),
        modifier = modifier.fillMaxWidth().padding(top = 16.dp)
    ) {
        DistanceInfoItem(
            text = orderDistance,
            icon = painterResource(Resources.images.iconPointOnMap),
            modifier = Modifier.weight(1f)
        )
        DistanceInfoItem(
            text = orderDuration,
            icon = painterResource(Resources.images.iconClock),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun DistanceInfoItem(text: String, icon: Painter, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .border(
                border = BorderStroke(width = 1.dp, color = Theme.colors.divider),
                shape = RoundedCornerShape(8.dp)
            ).padding(vertical = 8.dp)

    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = Theme.colors.contentTertiary
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
            text = text,
            color = Theme.colors.contentSecondary,
            style = Theme.typography.body,
        )
    }
}