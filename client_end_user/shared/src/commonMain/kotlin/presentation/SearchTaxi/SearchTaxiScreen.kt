package presentation.SearchTaxi

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.base.BaseScreen

class SearchTaxiScreen : BaseScreen<SearchTaxiScreenModel,SearchTaxiUIState, SearchTaxiUIEffect, SearchTaxiInteractionListener>() {


    override fun onEffect(effect: SearchTaxiUIEffect, navigator: Navigator) {
        when(effect){
            SearchTaxiUIEffect.onGoBack -> navigator.pop()
        }
    }

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    @Composable
    override fun onRender(state: SearchTaxiUIState, listener: SearchTaxiInteractionListener) {
        Box(){}
    }
}