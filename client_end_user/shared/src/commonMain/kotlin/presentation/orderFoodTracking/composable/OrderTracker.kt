package presentation.orderFoodTracking.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.composable.HorizontalDivider
import presentation.orderFoodTracking.OrderStatus
import resources.Resources

@OptIn(ExperimentalResourceApi::class)
@Composable
fun OrderTrackerCard(
    currentStatusDescription: String,
    orderStatus: OrderStatus,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Theme.colors.divider, shape = RoundedCornerShape(Theme.radius.medium))
            .background(Theme.colors.surface).padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currentStatusDescription,
                style = Theme.typography.titleLarge,
                color = Theme.colors.contentPrimary
            )
            EstimatedTimeWithIcon()
        }
        HorizontalDivider()
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            OrderStatus(
                hasFinished = orderStatus.isOrderPlaced,
                statusIcon = painterResource(Resources.images.icTime),
                contentDescription = "icon estimated time"
            )
            HorizontalDivider(thickness = 1.5.dp, orderHasFinished = orderStatus.isOrderInCooking)
            OrderStatus(
                hasFinished = orderStatus.isOrderInCooking,
                statusIcon = painterResource(Resources.images.icInCooking),
                contentDescription = "icon in cooking"
            )
            HorizontalDivider(thickness = 1.5.dp, orderHasFinished = orderStatus.isOrderInTheRoute)
            OrderStatus(
                hasFinished = orderStatus.isOrderInTheRoute,
                statusIcon = painterResource(Resources.images.icScooter),
                contentDescription = "icon scooter"
            )
            HorizontalDivider(thickness = 1.5.dp, orderHasFinished = orderStatus.isOrderArrived)
            OrderStatus(
                hasFinished = orderStatus.isOrderArrived,
                statusIcon = painterResource(Resources.images.icHome),
                contentDescription = "icon arrived home"
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EstimatedTimeWithIcon() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = painterResource(Resources.images.icTime),
            contentDescription = "time icon",
            tint = Theme.colors.contentPrimary,
        )
        Text(
            text = "32:00",
            style = Theme.typography.body,
            color = Theme.colors.contentPrimary,
        )
    }
}


@Composable
fun OrderStatus(
    hasFinished: Boolean,
    statusIcon: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    val statusColor by animateColorAsState(
        if (hasFinished) Theme.colors.primary
        else Theme.colors.contentTertiary
    )
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(RoundedCornerShape(Theme.radius.medium))
            .background(Theme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = statusIcon,
            contentDescription = contentDescription,
            tint = statusColor,
            modifier = Modifier.size(24.dp)
        )
    }
}