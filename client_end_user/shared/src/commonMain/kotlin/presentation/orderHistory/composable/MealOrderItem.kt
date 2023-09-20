package presentation.orderHistory.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.orderHistory.OrderHistoryUiState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MealOrderItem(
    modifier: Modifier = Modifier,
    orders: OrderHistoryUiState,
) {
    Column(
        modifier.fillMaxWidth().background(Theme.colors.background),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = modifier.size(64.dp),
                painter = painterResource(orders.restaurantImageUrl),
                contentDescription = "${orders.restaurantName} image",
                contentScale = ContentScale.Crop,
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
                        text = orders.createdAt.toString(),
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
        orders.meals.forEach { meal ->
            Text(
                text = "${meal.quantity} ${meal.mealName},",
                style = Theme.typography.caption,
                color = Theme.colors.contentTertiary
            )
        }

        SimpleLine(modifier.fillMaxWidth())
    }
}