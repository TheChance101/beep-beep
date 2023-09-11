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
    private var  selectedFood = mutableListOf<String>()

    override fun onPreferredFoodClicked(foodUIState: FoodUIState) {

        if(state.value.selectedPreferredFood.size < 4){
            addPreferredFood(foodUIState)
        }else{
            onSaved()
        }

    }
    private fun onSaved() {
        tryToExecute(
            { mangePreferredFood.savePreferredFood( state.value.selectedPreferredFood) },
            ::onSavedPreferredFoodSuccess,
            ::onError
        )
    }
    private fun addPreferredFood(foodUIState: FoodUIState) {
        selectedFood.add(foodUIState.name)
        val updatedPreferredCuisine = state.value.preferredFood.filterNot { it.name == foodUIState.name }
        updateState { it.copy(preferredFood=updatedPreferredCuisine,selectedPreferredFood = selectedFood) }
    }
    private fun onSavedPreferredFoodSuccess(unit:Unit) {
        sendNewEffect(PreferredFoodUIEffect.NavigateToPreferredScreen)
    }
    private fun onError(errorState: ErrorState) {
        println(errorState)
    }
}