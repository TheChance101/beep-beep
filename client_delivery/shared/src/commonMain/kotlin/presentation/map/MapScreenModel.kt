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
            updateState { it.copy(orderState = OrderState.NEW_ORDER) }
        }
    }
    override fun onAcceptClicked() {
        updateState { it.copy(orderState = OrderState.ACCEPTED) }
    }

    override fun onRejectClicked() {
        viewModelScope.launch {
            updateState { it.copy(orderState = OrderState.LOADING) }
            delay(2000) // just for simulation
            updateState { it.copy(orderState = OrderState.NEW_ORDER) }
        }
    }

    override fun onReceivedClicked() {
      updateState { it.copy(orderState = OrderState.RECEIVED) }
    }

    override fun onDeliveredClicked() {
        viewModelScope.launch {
            updateState { it.copy(orderState = OrderState.DELIVERED) }
            updateState { it.copy(orderState = OrderState.LOADING) }
            delay(2000)
            updateState { it.copy(orderState = OrderState.NEW_ORDER) }
        }
    }

    override fun onCloseClicked() {
        sendNewEffect(MapScreenUiEffect.CloseMap)
    }

}