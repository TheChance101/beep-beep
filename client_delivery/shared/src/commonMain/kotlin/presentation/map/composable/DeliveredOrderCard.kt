package presentation.map.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpButton
import presentation.map.DeliveredOrderInteractionsListener
import presentation.map.MapScreenUiState
import resources.Resources

@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun DeliveredOrderCard(state: MapScreenUiState, listener: DeliveredOrderInteractionsListener) {
    MapCard {
        OrderInfo(
            restaurantImageUrl = state.orderUiState.restaurantImageUrl,
            restaurantName = state.orderUiState.restaurantName,
            restaurantLocation = state.orderUiState.restaurantAddress,
            orderLocation = state.orderUiState.destinationAddress
        )
        BpButton(
            onClick = listener::onDeliveredClicked,
            title = Resources.strings.delivered,
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            isLoading = state.isLoading,
            enabled = state.isButtonEnabled
        )
    }
}