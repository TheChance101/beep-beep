package presentation.map

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.base.BaseScreen

class MapScreen :
    BaseScreen<MapScreenModel, MapScreenUiState, MapUiEffect, MapScreenInteractionListener>() {

    @Composable
    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
    override fun onRender(state: MapScreenUiState, listener: MapScreenInteractionListener) {
        TODO("Not yet implemented")
    }

    override fun onEffect(effect: MapUiEffect, navigator: Navigator) {

    }

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

}