package presentation.taxi

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composable.BackButton
import presentation.orderFoodTracking.OrderFoodTrackingUiState
import presentation.orderFoodTracking.composables.MapView
import resources.Resources
import util.getNavigationBarPadding
import util.getStatusBarPadding

class TaxiOrderScreen :
    BaseScreen<TaxiOrderScreenModel, TaxiOrderUiState, TaxiOrderUiEffect, TaxiOrderInteractionListener>() {
    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: TaxiOrderUiEffect, navigator: Navigator) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun onRender(state: TaxiOrderUiState, listener: TaxiOrderInteractionListener) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Theme.colors.background)
                .padding(getStatusBarPadding())
        ) {
            MapView(
                modifier = Modifier.fillMaxSize(),
                currentLocation = state.currentLocation,
                destination = state.destination
            )
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
            ) {
                BackButton(
                    modifier = Modifier.padding(0.dp),
                    onClick = { listener.onBackButtonClicked() }
                )
                TaxiCard(
                    state = state,
                    listener = listener,
                    modifier = Modifier.padding(getNavigationBarPadding()).padding(16.dp)
                )
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
    @Composable
    private fun TaxiCard(
        modifier: Modifier = Modifier,
        state: TaxiOrderUiState,
        listener: TaxiOrderInteractionListener,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Theme.radius.medium))
                .border(1.dp, Theme.colors.divider, shape = RoundedCornerShape(Theme.radius.medium))
                .background(Theme.colors.surface)
                .padding(vertical = 24.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = Resources.strings.whereToGo,
                style = Theme.typography.titleLarge,
                color = Theme.colors.contentPrimary,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            BpSimpleTextField(
                state.query,
                hint = Resources.strings.searchDestinationHint,
                hintColor = Theme.colors.contentSecondary,
                onValueChange = listener::onSearchInputChanged,
                onClick = {},
                leadingPainter = painterResource(Resources.images.searchOutlined),
                modifier = Modifier.background(Theme.colors.background),
            )

            BpButton(
                modifier = Modifier.fillMaxWidth(),
                title = Resources.strings.setDestination,
                onClick = {},
            )
        }
    }

}
