package presentation.home

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class HomeScreenModel :
    BaseScreenModel<HomeScreenUIState, HomeScreenUIEffect>(HomeScreenUIState()),
    HomeScreenInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope

}