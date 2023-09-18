package presentation.map.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.map.MapScreenInteractionsListener
import presentation.map.MapScreenUiState
import resources.Resources

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewOrderCard(state: MapScreenUiState, listener: MapScreenInteractionsListener) {
    MapCard {
        Text(
            text = Resources.strings.newOrder,
            color = Theme.colors.contentPrimary,
            style = Theme.typography.headline,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )
        OrderInfo(
            restaurantImage = painterResource(Resources.images.test),//just for now,then will be from state,
            restaurantName = state.restaurantName,
            restaurantLocation = state.restaurantLocation,
            orderLocation = state.orderLocation
        )
        BpButton(
            onClick = listener::onAcceptClicked,
            title = Resources.strings.accept,
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
        )
        BpTransparentButton(
            onClick = listener::onRejectClicked,
            title = Resources.strings.reject,
            modifier = Modifier.fillMaxWidth().height(56.dp).padding(top = 8.dp, bottom = 8.dp),
        )
    }
}



