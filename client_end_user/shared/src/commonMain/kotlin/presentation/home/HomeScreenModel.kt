package presentation.home

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class HomeScreenModel() : BaseScreenModel<HomeScreenUiState, HomeScreenUiEffect>(HomeScreenUiState()),
    HomeScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope
}