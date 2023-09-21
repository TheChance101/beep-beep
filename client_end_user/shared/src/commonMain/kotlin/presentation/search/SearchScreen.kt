package presentation.search

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.base.BaseScreen

class SearchScreen :
    BaseScreen<SearchScreenModel, SearchUiState, SearchUiEffect, SearchInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    @Composable
    override fun onRender(state: SearchUiState, listener: SearchInteractionListener) {
        TODO("Not yet implemented")
    }

    override fun onEffect(effect: SearchUiEffect, navigator: Navigator) {
        TODO("Not yet implemented")
    }


}
