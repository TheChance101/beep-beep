package presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.base.BaseScreen
import presentation.composable.ImageSlider


class HomeScreen :
    BaseScreen<HomeScreenModel, HomeScreenUIState, HomeScreenUIEffect, HomeScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: HomeScreenUIEffect, navigator: Navigator) {

    }

    @Composable
    override fun onRender(state: HomeScreenUIState, listener: HomeScreenInteractionListener) {
        Column(modifier = Modifier.fillMaxSize().background(Theme.colors.background)) {
            ImageSlider(
                modifier = Modifier.padding(16.dp),
                images = state.offers
            ) { id ->
            }
        }
    }

}