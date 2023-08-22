package presentation.meals

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IGetCousinUseCase
import domain.usecase.IGetMealsByCousin
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class MealsScreenModel :
    BaseScreenModel<MealsScreenUIState, MealsScreenUIEffect>(MealsScreenUIState()),
    MealScreenInteractionListener {

    private val getCousinUse: IGetCousinUseCase by inject()
    private val getMealsUseCase: IGetMealsByCousin by inject()

    init {
        getCousin()
    }

    private fun getCousin() {
        updateState { it.copy(isLoading = true) }

        tryToExecute(
            function = {
                getCousinUse("1")
            },
            onSuccess = { value ->
                updateState {
                    it.copy(
                        cousin = value.toUIState(),
                        selectedCousin = value[0].toUIState(),
                        isLoading = false
                    )
                }
                getMeals(state.value.selectedCousin!!.id)
            },
            onError = {
                updateState { it.copy(error = it.error, isLoading = false) }
            }
        )
    }

    private fun getMeals(id: String) {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = {
                getMealsUseCase(id)
            },
            onSuccess = { value ->
                updateState { it.copy(meals = value.toUIState(), isLoading = false) }
            },
            onError = {
                updateState { it.copy(error = it.error, isLoading = false) }
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
        getMeals()
    }
}
