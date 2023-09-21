package presentation.orderHistory.composable

import androidx.compose.foundation.Image
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
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.composable.BpImageLoader
import presentation.orderHistory.OrderHistoryUiState
import resources.Resources

@OptIn(ExperimentalResourceApi::class)
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
                        text = "Oct 2/2022",
//                        text = orders.createdAt.toString(),
                        style = Theme.typography.caption,
                        color = Theme.colors.contentTertiary
                    )
                }
                Text(
                    text = "$ ${orders.totalPrice}",
                    style = Theme.typography.titleLarge,
                    color = Theme.colors.contentPrimary
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            orders.meals.forEach { meal ->
                Text(
                    text = "${meal.quantity} ${meal.mealName}, ",
                    style = Theme.typography.caption,
                    color = Theme.colors.contentTertiary
                )
            }
        }

    }
}