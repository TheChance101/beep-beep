package presentation.map

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel

class MapScreenModel:BaseScreenModel<MapScreenUiState,MapScreenUiEffect>(MapScreenUiState()),MapScreenInteractionsListener {

    override val viewModelScope: CoroutineScope = coroutineScope
    init {
        viewModelScope.launch {
            delay(5000)
            updateState { it.copy(isLoading = false) }
        }
    }
    override fun onAcceptClicked() {
        updateState { it.copy(isOrderAccepted = true) }
    }

    override fun onRejectClicked() {
        TODO("Not yet implemented")
    }

    override fun onReceivedClicked() {
        TODO("Not yet implemented")
    }

    override fun onDeliveredClicked() {
        TODO("Not yet implemented")
    }

    override fun onCloseClicked() {
        sendNewEffect(MapScreenUiEffect.CloseMap)
    }

}