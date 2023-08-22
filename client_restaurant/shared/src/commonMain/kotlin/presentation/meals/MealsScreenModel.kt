package presentation.meals

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class MealsScreenModel :
    BaseScreenModel<MealsScreenUIState, MealsScreenUIEffect>(MealsScreenUIState()),
    MealScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    override fun onClickBack() {
        sendNewEffect(MealsScreenUIEffect.Back)
    }

    override fun onClickMeal() {
        sendNewEffect(MealsScreenUIEffect.NavigateToMealDetails)
    }

    override fun onClickCousinType(type: MealsScreenUIState.CousinUIState) {
        updateState { it.copy(selectedCousin = type) }
    }


}
