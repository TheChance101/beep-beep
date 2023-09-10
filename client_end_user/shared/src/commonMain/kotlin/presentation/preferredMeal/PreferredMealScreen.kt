package presentation.preferredMeal

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.base.BaseScreen
import presentation.main.MainContainer

class PreferredMealScreen :
    BaseScreen<PreferredScreenModel, PreferredScreenUiState, PreferredScreenUiEffect, PreferredScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: PreferredScreenUiEffect, navigator: Navigator) {
        when(effect){
            PreferredScreenUiEffect.NavigateToHomeScreen -> navigator.push(MainContainer)
        }
    }

    @Composable
    override fun onRender(
        state: PreferredScreenUiState,
        listener: PreferredScreenInteractionListener
    ) {
        TODO("Not yet implemented")
    }
}