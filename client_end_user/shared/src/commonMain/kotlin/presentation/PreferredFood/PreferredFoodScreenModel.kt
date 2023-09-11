package presentation.PreferredFood
import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IMangePreferredFoodUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import presentation.PreferredFood.FoodUIState
import presentation.PreferredFood.PreferredFoodInteractionListener
import presentation.PreferredFood.PreferredFoodUIEffect
import presentation.PreferredFood.PreferredFoodUIState
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class PreferredFoodScreenModel(
 private val  mangePreferredFood: IMangePreferredFoodUseCase
) :BaseScreenModel<PreferredFoodUIState, PreferredFoodUIEffect,>(PreferredFoodUIState()),
    PreferredFoodInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    override fun onPreferredFoodClicked(foodUIState: FoodUIState) {

        val updatedPreferredCuisine = state.value.preferredFood.filterNot { it.name == foodUIState.name }

        updateState { it.copy(preferredFood=updatedPreferredCuisine) }
//        tryToExecute(
//            { mangePreferredFood.savePreferredFood(foodUIState.name) },
//            ::onSavedPreferredFoodSuccess,
//            ::onError
//        )
    }
    private fun onSavedPreferredFoodSuccess(unit:Unit) {
        sendNewEffect(PreferredFoodUIEffect.NavigateToPreferredScreen)
    }
    private fun onError(errorState: ErrorState) {
        println(errorState)
    }
}