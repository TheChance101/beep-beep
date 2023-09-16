package presentation.map.composable

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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resources.Resources

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewOrderRequest() {
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
                modifier = Modifier.size(48.dp)
                    .clip(shape = RoundedCornerShape(4.dp))
                    .border(width = 1.dp, color = Theme.colors.divider, shape = RectangleShape),
                painter = painterResource(Resources.images.test),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                Text(
                    text = "Restaurant Name",
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
                        text = "Restaurant Location",
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
                    text = "Alex,Egypt",
                    style = Theme.typography.body,
                    color = Theme.colors.contentPrimary
                )
            }
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