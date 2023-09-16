package presentation.map.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.map.MapScreenInteractionsListener
import presentation.map.MapScreenUiState
import resources.Resources

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewOrderRequest(state: MapScreenUiState, listener: MapScreenInteractionsListener) {
    MapCard {
        Text(
            text = Resources.strings.newOrder,
            color = Theme.colors.contentPrimary,
            style = Theme.typography.headline,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )
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
                painter = painterResource(Resources.images.test),//just for now,then will be from state
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                Text(
                    text = state.restaurantName,
                    color = Theme.colors.contentPrimary,
                    style = Theme.typography.titleLarge,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(Resources.images.iconMapPoint),
                        contentDescription = null,
                    )
                    Text(
                        text = state.restaurantLocation,
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
                    text = state.orderLocation,
                    style = Theme.typography.body,
                    color = Theme.colors.contentPrimary
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                12.dp,
                alignment = Alignment.CenterHorizontally
            ),
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            DistanceInfo(
                text = state.orderDistance,
                icon = painterResource(Resources.images.iconPointOnMap)
            )
            DistanceInfo(
                text = state.orderDuration,
                icon = painterResource(Resources.images.iconClock)
            )
        }

        BpButton(
            onClick = {},
            title = Resources.strings.accept,
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
        )
        BpTransparentButton(
            onClick = {},
            title = Resources.strings.reject,
            modifier = Modifier.fillMaxWidth().height(56.dp).padding(top = 8.dp, bottom = 8.dp),
        )
    }
}

@Composable
private fun DistanceInfo(text: String, icon: Painter) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .border(
                border = BorderStroke(width = 1.dp, color = Theme.colors.divider),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 8.dp, horizontal = 44.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
            text = text,
            color = Theme.colors.contentSecondary,
            style = Theme.typography.body,
        )
    }
}