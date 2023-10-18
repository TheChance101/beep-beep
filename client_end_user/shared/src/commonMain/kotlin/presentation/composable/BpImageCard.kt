package presentation.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.beepbeep.designSystem.ui.composable.modifier.noRippleEffect
import com.beepbeep.designSystem.ui.theme.Theme
import domain.entity.PriceLevel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resources.Resources

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BpImageCard(
    onClickCard: (String) -> Unit = {},
    imageUrl: String,
    title: String,
    id: String = "",
    rate: Double = 0.0,
    priceLevel: PriceLevel = PriceLevel.MEDIUM,
    modifier: Modifier = Modifier,
    hasOffer: Boolean = false,
    hasRate: Boolean = true,
    offer: String = "",
    hasDeliveryPrice: Boolean = false,
    deliveryPrice: String = "",
    hasPriceLevel: Boolean = true,
    hasPrice: Boolean = false,
    price: Double = 0.0,
    currency: String = "",
) {
    Card(
        modifier = modifier.noRippleEffect { onClickCard(id) },
        colors = CardDefaults.cardColors(Color.Transparent),
        shape = RoundedCornerShape(0.dp)
    ) {
        Box(
            modifier = Modifier.width(232.dp)
                .height(120.dp).clip(RoundedCornerShape(8.dp))
        ) {

            BpImageLoader(imageUrl = imageUrl, contentScale = ContentScale.Crop)

            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                AnimatedVisibility(hasOffer || hasDeliveryPrice) {
                    Row(
                        modifier = Modifier.padding(top = 8.dp, end = 8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (hasOffer) Theme.colors.secondary else Theme.colors.successContainer)
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (hasOffer) {
                            Text(
                                text = offer,
                                modifier = Modifier.padding(horizontal = 4.dp),
                                style = Theme.typography.caption,
                                color = Theme.colors.primary
                            )
                        } else {
                            Icon(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                painter = painterResource(Resources.images.scooter),
                                tint = Theme.colors.success,
                                contentDescription = null
                            )
                        }
                        Text(
                            text = if (hasOffer) Resources.strings.off else deliveryPrice,
                            modifier = Modifier.padding(end = 4.dp),
                            style = Theme.typography.caption,
                            color = if (hasOffer) Theme.colors.primary else Theme.colors.success
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier.padding(top = 8.dp).width(232.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary
            )
            Spacer(Modifier.weight(1f))
            if (hasRate) {
                Image(
                    modifier = Modifier.padding(end = 4.dp).size(16.dp),
                    painter = painterResource(Resources.images.filledStar),
                    contentDescription = null
                )
                Text(
                    text = rate.toString(),
                    modifier = Modifier.padding(end = 4.dp),
                    style = Theme.typography.caption,
                    color = Theme.colors.contentSecondary
                )
                Spacer(
                    modifier.padding(end = 4.dp).clip(CircleShape).size(4.dp)
                        .background(Theme.colors.disable)
                )
            }
            if (hasPriceLevel) BpPriceLevel(priceLevel)
            if (hasPrice) Text(
                " $currency $price",
                style = Theme.typography.body,
                color = Theme.colors.primary
            )
        }
    }
}
