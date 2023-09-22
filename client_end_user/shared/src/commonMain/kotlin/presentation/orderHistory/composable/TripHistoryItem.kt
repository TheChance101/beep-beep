package presentation.orderHistory.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.orderHistory.TripHistoryUiState
import resources.Resources

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TripHistoryItem(
    tripState: TripHistoryUiState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "16 Dec 2022, 20:30",
//              text = tripState.endDate,
                style = Theme.typography.title,
                color = Theme.colors.contentPrimary
            )
            Text(
                "$ ${tripState.price}",
                style = Theme.typography.titleLarge,
                color = Theme.colors.contentPrimary
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                tripState.driverName,
                style = Theme.typography.title,
                color = Theme.colors.contentSecondary
            )
            Text(
                tripState.taxiPlateNumber,
                style = Theme.typography.body,
                color = Theme.colors.contentTertiary
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                modifier = Modifier.fillMaxHeight().width(24.dp),
                painter = painterResource(Resources.images.transferIcon),
                contentDescription = "",
                tint = Theme.colors.primary
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
//                    tripState.startPoint.toString(),
                    "Cairo,Egypt",
                    style = Theme.typography.body,
                    color = Theme.colors.contentTertiary
                )
                Text(
//                    tripState.destination.toString(),
                    "Almenoufia",
                    style = Theme.typography.body,
                    color = Theme.colors.contentTertiary
                )
            }
        }
    }
}