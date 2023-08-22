package presentation.composables

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.order.OrderMealUiState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun OrderCard(
    modifier: Modifier = Modifier,
    items: List<OrderMealUiState>,
    totalPrice: Double,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .background(Theme.colors.surface)
            .clip(RoundedCornerShape(Theme.radius.medium))
            .padding(Theme.dimens.space16)
    ) {
        LazyColumn {
            items(items) {
                OrderItem(
                    painter = painterResource(it.mealImageUrl),
                    mealName = it.mealName,
                    quantity = it.quantity
                )
            }
        }
        BPDashedDivider(modifier = Modifier.padding(vertical = Theme.dimens.space16))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = "Total price:",
                    style = Theme.typography.caption,
                    color = Theme.colors.contentTertiary
                )
                Text(
                    text = "$ $totalPrice",
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
    painter: Painter,
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
            painter = painter,
            contentScale = ContentScale.Crop,
            contentDescription = "order image"
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Theme.dimens.space8),
        ) {
            Text(
                text = mealName,
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary
            )
            Text(
                text = "Qty - $quantity",
                style = Theme.typography.caption,
                color = Theme.colors.contentTertiary
            )
        }
    }
}