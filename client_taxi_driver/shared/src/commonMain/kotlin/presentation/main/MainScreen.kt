package presentation.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.base.BaseScreen

class MainScreen:
    BaseScreen<MainScreenModel, MainScreenUiState, MainUiEffect, MainScreenInteractionListener>() {

    @Composable
    override fun onRender(state: MainScreenUiState, listener: MainScreenInteractionListener) {
        Text("hello taxi")
    }

    override fun onEffect(effect: MainUiEffect, navigator: Navigator) {

    }

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

}
