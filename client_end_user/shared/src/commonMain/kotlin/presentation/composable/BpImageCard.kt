package presentation.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import domain.entity.PriceLevel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resources.Resources

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BpImageCard(
    painter: Painter,
    rate: Double,
    title: String,
    priceLevel: PriceLevel,
    modifier: Modifier = Modifier,
    hasOffer: Boolean = false,
    offer: String = "",
    hasDeliveryPrice: Boolean = false,
    deliveryPrice: String = ""
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(Color.Transparent),
        shape = RoundedCornerShape(0.dp)
    ) {
        Box(
            modifier = Modifier.width(232.dp)
                .height(120.dp).clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Row(
                modifier = Modifier.padding(start = 179.dp, top = 8.dp)
                    .clip(RoundedCornerShape(4.dp)).background(Theme.colors.surface),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = rate.toString(),
                    modifier = Modifier.padding(4.dp),
                    style = Theme.typography.caption,
                    color = Theme.colors.contentSecondary
                )
                Image(
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp, end = 4.dp).size(16.dp),
                    painter = painterResource(Resources.images.filledStar),
                    contentDescription = null
                )
            }
        }
        Row(
            modifier = Modifier.padding(top = 8.dp).width(232.dp)
        ) {
            Text(
                text = title,
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary
            )
            Spacer(Modifier.weight(1f))
            BpPriceLevel(priceLevel)
        }
        AnimatedVisibility(hasOffer) {
            Row(
                modifier = Modifier.padding(top = 8.dp).clip(RoundedCornerShape(4.dp))
                    .background(Theme.colors.secondary)
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = offer,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    style = Theme.typography.caption,
                    color = Theme.colors.primary
                )
                Text(
                    text = Resources.strings.off,
                    modifier = Modifier.padding(end = 4.dp),
                    style = Theme.typography.caption,
                    color = Theme.colors.primary
                )
            }
        }
        AnimatedVisibility(hasDeliveryPrice) {
            Row(
                modifier = Modifier.padding(top = 8.dp).clip(RoundedCornerShape(4.dp))
                    .background(Theme.colors.successContainer)
                    .padding(vertical = 4.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(start = 4.dp),
                    painter = painterResource(Resources.images.scooter),
                    tint = Theme.colors.success,
                    contentDescription = null
                )
                Text(
                    text = deliveryPrice,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    style = Theme.typography.caption,
                    color = Theme.colors.success
                )
            }
        }
    }
}