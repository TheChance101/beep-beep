package presentation.order.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import com.seiko.imageloader.rememberAsyncImagePainter
import presentation.composable.BPDashedDivider
import presentation.order.OrderUiState
import resources.Resources

@Composable
fun OrderCard(
    order: OrderUiState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.medium))
            .background(Theme.colors.surface)
            .padding(Theme.dimens.space16),
        verticalArrangement = Arrangement.spacedBy(Theme.dimens.space16),
    ) {
        order.orderMealUiStates.forEach { order ->
            OrderItem(
                imageUrl = order.mealImageUrl,
                mealName = order.mealName,
                quantity = order.quantity
            )
        }

        BPDashedDivider(showDiamondIcon = true)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = Resources.strings.totalPrice,
                    style = Theme.typography.caption,
                    color = Theme.colors.contentTertiary
                )
                Text(
                    text = "${Resources.strings.dollarSign} ${order.totalPrice}",
                    style = Theme.typography.titleLarge,
                    color = Theme.colors.contentPrimary
                )
            }
            content()
        }
    }
}

@Composable
private fun OrderItem(
    imageUrl: String,
    mealName: String,
    quantity: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth().height(56.dp),
        horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8),
    ) {
        Image(
            modifier = Modifier.fillMaxHeight().width(80.dp)
                .clip(RoundedCornerShape(Theme.radius.medium)),
            painter = rememberAsyncImagePainter(imageUrl),
            contentScale = ContentScale.Crop,
            contentDescription = Resources.strings.orderImageContentDescription
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                Theme.dimens.space8,
                alignment = Alignment.CenterVertically
            ),
        ) {
            Text(
                text = mealName,
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary
            )
            Text(
                text = "${Resources.strings.quantity} - $quantity",
                style = Theme.typography.caption,
                color = Theme.colors.contentTertiary
            )
        }
    }
}