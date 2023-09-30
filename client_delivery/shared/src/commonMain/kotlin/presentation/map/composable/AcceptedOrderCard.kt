package presentation.map.composable


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpButton
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.map.AcceptedOrderInteractionsListener
import presentation.map.MapScreenUiState
import resources.Resources

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AcceptedOrderCard(state: MapScreenUiState, listener: AcceptedOrderInteractionsListener) {
    MapCard {
        OrderInfo(
            restaurantImage = painterResource(Resources.images.test),//just for now,then will be from state,
            restaurantName = state.restaurantName,
            restaurantLocation = state.restaurantLocation,
            orderLocation = state.orderLocation
        )
        DistanceInfo(orderDistance = state.orderDistance, orderDuration = state.orderDuration)

        BpButton(
            onClick = listener::onReceivedClicked,
            title = Resources.strings.received,
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
        )
    }
}