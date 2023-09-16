package presentation.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import presentation.base.BaseScreen
import presentation.map.composable.NewOrderRequest


class MapScreen:BaseScreen<MapScreenModel,MapScreenUiState,MapScreenUiEffect,MapScreenInteractionsListener>() {
    @Composable
    override fun onRender(state: MapScreenUiState, listener: MapScreenInteractionsListener) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(modifier = Modifier.align(Alignment.BottomCenter),
                visible = true,
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut()
            ) {
                NewOrderRequest(state, listener)
            }
        }
    }

    override fun onEffect(effect: MapScreenUiEffect, navigator: Navigator) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }
}
