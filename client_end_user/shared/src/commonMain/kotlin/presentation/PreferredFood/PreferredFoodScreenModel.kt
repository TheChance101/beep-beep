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
        viewModelScope.launch {
            mangePreferredFood.savePreferredFood(foodUIState.name)
            onSavedPreferredFoodSuccess()
        }
    }
    private fun onSavedPreferredFoodSuccess() {
        sendNewEffect(PreferredFoodUIEffect.NavigateToPreferredScreen)
    }
}