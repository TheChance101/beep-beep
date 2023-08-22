package presentation.meals

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IGetCousinUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class MealsScreenModel:
    BaseScreenModel<MealsScreenUIState, MealsScreenUIEffect>(MealsScreenUIState()),
    MealScreenInteractionListener {

    private  val getCousinUse: IGetCousinUseCase by inject()
    init {
        getCousin()
    }

    private  fun getCousin(){
        tryToExecute(
            function = {
                getCousinUse("1").toUIState()
            },
            onSuccess = {value->
                var result =value
                    updateState { it.copy(cousin = result) }
            },
            onError = {
                updateState { it.copy(error = it.error) }
            }
        )
    }

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
