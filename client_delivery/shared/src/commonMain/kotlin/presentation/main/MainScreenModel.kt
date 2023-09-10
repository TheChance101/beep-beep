package presentation.main

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class MainScreenModel : BaseScreenModel<MainUiState, MainScreenUiEffect>(MainUiState()),
    MainInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope


}