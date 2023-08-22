package presentation.main

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class MainScreenModel : BaseScreenModel<MainScreenUIState, MainScreenUIEffect>(MainScreenUIState()), MainScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    override fun onClickBack() {
        sendNewEffect(MainScreenUIEffect.Back)
    }
}