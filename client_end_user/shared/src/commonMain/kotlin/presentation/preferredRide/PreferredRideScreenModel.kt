package presentation.preferredRide

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cost
import domain.entity.PreferredRide
import domain.entity.RideQuality
import domain.usecase.ManageUserUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class PreferredRideScreenModel(private val userPreference: ManageUserUseCase)  : BaseScreenModel<PreferredRideUiState, PreferredRideUiEffect>
    (PreferredRideUiState()), PreferredRideInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope
    override fun onClickPreferredRide(flag: Int) {
        if (flag == 0) {
            updateState { it.copy(cost = Cost.HIGH, quality = RideQuality.HIGH) }
        } else if (flag == 1) {
            updateState { it.copy(cost = Cost.LOW, quality = RideQuality.LOW) }
        }
        savePreferredRide(state.value.toPreferredRide())
    }

    private fun savePreferredRide(preferredRide: PreferredRide) {
        tryToExecute(
            { userPreference.savePreferredRide(preferredRide) },
            ::onSuccess,
            ::onError
        )
    }

    private fun onSuccess(unit: Unit) {
        sendNewEffect(PreferredRideUiEffect.NavigateToPreferredMeal)
    }
    private fun onError(errorState: ErrorState) {
        println("$errorState")
    }



}