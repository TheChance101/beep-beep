package presentation.map.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resources.Resources

@OptIn(ExperimentalResourceApi::class)
@Composable
fun OrderInfo(
    restaurantImage: Painter,
    restaurantName: String,
    restaurantLocation: String,
    orderLocation: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(bottom = 28.dp)
    ) {
        Image(
            modifier = Modifier.size(48.dp).clip(shape = RoundedCornerShape(4.dp))
                .border(
                    border = BorderStroke(width = 1.dp, color = Theme.colors.divider),
                    shape = RoundedCornerShape(4.dp)
                ),
            painter = restaurantImage,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = restaurantName,
                color = Theme.colors.contentPrimary,
                style = Theme.typography.titleLarge,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Resources.images.iconMapPoint),
                    contentDescription = null,
                    tint = Theme.colors.contentTertiary
                )
                Text(
                    text = restaurantLocation,
                    color = Theme.colors.contentSecondary,
                    style = Theme.typography.caption,
                )
            }
        }
    }
    Divider(
        color = Theme.colors.divider,
        thickness = 1.dp
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(Resources.images.iconLocation),
            contentDescription = null, modifier = Modifier.padding(top = 8.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = Resources.strings.deliverAt,
                style = Theme.typography.caption,
                color = Theme.colors.contentTertiary
            )
            Text(
                text = orderLocation,
                style = Theme.typography.body,
                color = Theme.colors.contentPrimary
            )
        }
    }
}