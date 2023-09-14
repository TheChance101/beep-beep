package presentation.map

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.base.BaseScreen
import presentation.map.composable.FindingRideCard

class MapScreen :
    BaseScreen<MapScreenModel, MapScreenUiState, MapUiEffect, MapScreenInteractionListener>() {

    @Composable
    override fun onRender(state: MapScreenUiState, listener: MapScreenInteractionListener) {
        FindingRideCard()
    }

    override fun onEffect(effect: MapUiEffect, navigator: Navigator) {

    }

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

}