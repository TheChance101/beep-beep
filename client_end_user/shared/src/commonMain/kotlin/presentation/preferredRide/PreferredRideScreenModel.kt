package presentation.preferredRide

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class PreferredRideScreenModel : BaseScreenModel<PreferredRideUiState, PreferredRideUiEffect>
    (PreferredRideUiState()), PreferredRideInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope
    override fun onClickPreferredRide(flag: Int) {
        if (flag == 0) {
            updateState { it.copy(cost = Cost.HIGH, quality = RideQuality.HIGH) }
        } else if (flag == 1) {
            updateState { it.copy(cost = Cost.LOW, quality = RideQuality.LOW) }
        }
    }


}