package presentation.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import presentation.base.BaseScreen
import presentation.map.composable.FindingRideCard

class MapScreen :
    BaseScreen<MapScreenModel, MapScreenUiState, MapUiEffect, MapScreenInteractionListener>() {

    @Composable
    override fun onRender(state: MapScreenUiState, listener: MapScreenInteractionListener) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            FindingRideCard(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }

    override fun onEffect(effect: MapUiEffect, navigator: Navigator) {

    }

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

}