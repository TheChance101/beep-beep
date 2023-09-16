package presentation.map

import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class MapScreenModel:BaseScreenModel<MapScreenUiState,MapScreenUiEffect>(MapScreenUiState()),MapScreenInteractionsListener {

    override val viewModelScope: CoroutineScope
        get() = TODO("Not yet implemented")

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