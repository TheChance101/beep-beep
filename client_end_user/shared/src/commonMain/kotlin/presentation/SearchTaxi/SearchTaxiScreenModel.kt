package presentation.SearchTaxi

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class SearchTaxiScreenModel : BaseScreenModel<SearchTaxiUIState, SearchTaxiUIEffect>(SearchTaxiUIState()),SearchTaxiInteractionListener{

    override val viewModelScope: CoroutineScope = coroutineScope

    override fun onGoBack() {
        sendNewEffect(SearchTaxiUIEffect.onGoBack)
    }

}