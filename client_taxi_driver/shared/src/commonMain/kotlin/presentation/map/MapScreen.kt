package presentation.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAppBar
import presentation.base.BaseScreen
import presentation.map.composable.FindingRideCard
import presentation.resources.Resources

class MapScreen :
    BaseScreen<MapScreenModel, MapScreenUiState, MapUiEffect, MapScreenInteractionListener>() {

    @Composable
    override fun onRender(state: MapScreenUiState, listener: MapScreenInteractionListener) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            BpAppBar(
                isBackIconVisible = false,
                title = Resources.strings.welcomeDriver
            ) {
                Icon(
                    modifier = Modifier.padding(end = 16.dp),
                    imageVector = Icons.Filled.Close,
                    contentDescription = null
                )
            }
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