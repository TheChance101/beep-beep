package presentation.main

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.base.BaseScreen

class MainScreen :
    BaseScreen<MainScreenModel, MainUiState, MainScreenUiEffect, MainInteractionListener>() {

    @Composable
    override fun onRender(state: MainUiState, listener: MainInteractionListener) {
        TODO("Not yet implemented")
    }


    override fun onEffect(effect: MainScreenUiEffect, navigator: Navigator) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun Content() {
        TODO("Not yet implemented")
    }
}