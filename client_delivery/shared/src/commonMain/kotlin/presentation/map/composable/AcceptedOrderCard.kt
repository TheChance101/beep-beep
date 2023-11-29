package presentation.map.composable


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpButton
import presentation.map.AcceptedOrderInteractionsListener
import presentation.map.MapScreenUiState
import resources.Resources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcceptedOrderCard(state: MapScreenUiState, listener: AcceptedOrderInteractionsListener) {
    MapCard {
        OrderInfo(
            restaurantImageUrl = state.orderUiState.restaurantImageUrl,
            restaurantName = state.orderUiState.restaurantName,
            restaurantLocation = state.orderUiState.restaurantAddress,
            orderLocation = state.orderUiState.destinationAddress
        )

        DistanceInfo(
            orderDistance = "${state.orderDistance} KM",
            orderDuration = "${state.orderDuration} min"
        )

        BpButton(
            onClick = listener::onReceivedClicked,
            title = Resources.strings.received,
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            isLoading = state.isLoading,
            enabled = state.isButtonEnabled
        )
    }
}