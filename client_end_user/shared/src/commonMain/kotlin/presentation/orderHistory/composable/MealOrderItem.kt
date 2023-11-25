package presentation.orderHistory.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpImageLoader
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.orderHistory.OrderHistoryUiState
import resources.Resources

@Composable
fun MealOrderItem(
    modifier: Modifier = Modifier,
    orders: OrderHistoryUiState,
) {
    Column(
        modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BpImageLoader(
                modifier = modifier
                    .size(64.dp)
                    .clip(shape = RoundedCornerShape(Theme.radius.medium)),
                imageUrl = orders.restaurantImageUrl,
                errorPlaceholderImageUrl = Resources.images.restaurantErrorPlaceholder,
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = orders.restaurantName,
                        style = Theme.typography.title,
                        color = Theme.colors.contentPrimary
                    )
                    Text(
                        text = orders.createdAt,
                        style = Theme.typography.caption,
                        color = Theme.colors.contentTertiary
                    )
                }
                Text(
                    text = "${orders.currency} ${orders.totalPrice}",
                    style = Theme.typography.titleLarge,
                    color = Theme.colors.contentPrimary
                )
            }
        }

        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
            text = orders.meals,
            style = Theme.typography.caption,
            color = Theme.colors.contentTertiary
        )
    }
}
