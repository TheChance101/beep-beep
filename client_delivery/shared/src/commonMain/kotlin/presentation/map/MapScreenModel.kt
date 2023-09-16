package presentation.map

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class MapScreenModel:BaseScreenModel<MapScreenUiState,MapScreenUiEffect>(MapScreenUiState()),MapScreenInteractionsListener {

    override val viewModelScope: CoroutineScope = coroutineScope

    override fun onAcceptClicked() {
        TODO("Not yet implemented")
    }

    override fun onRejectClicked() {
        TODO("Not yet implemented")
    }

    override fun onCloseClicked() {
        sendNewEffect(MapScreenUiEffect.CloseMap)
    }

}