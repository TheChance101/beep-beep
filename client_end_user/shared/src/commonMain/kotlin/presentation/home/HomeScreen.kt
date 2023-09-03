package presentation.home

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.base.BaseScreen

class HomeScreen : BaseScreen<HomeScreenModel, HomeScreenUiState, HomeScreenUiEffect, HomeScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: HomeScreenUiEffect, navigator: Navigator) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun onRender(state: HomeScreenUiState, listener: HomeScreenInteractionListener) {

    }

}