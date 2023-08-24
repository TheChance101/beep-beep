package presentation.meals

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IGetCousinUseCase
import domain.usecase.IGetMealsByCousin
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.meals.MealsUIState.CousinUIState
import presentation.meals.MealsUIState.MealsScreenUIState
import presentation.meals.MealsUIState.toCousinUIState
import presentation.meals.MealsUIState.toUIState

class MealsScreenModel :
    BaseScreenModel<MealsScreenUIState, MealsScreenUIEffect>(MealsScreenUIState()),
    MealScreenInteractionListener  {
    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    private val getCousinUse: IGetCousinUseCase by inject()
    private val getMealsUseCase: IGetMealsByCousin by inject()

    init {
        getCousin()
    }

    private fun getCousin() {
        tryToExecute(
            function = {
                getCousinUse("1")

            },
            onSuccess = { value ->
                updateState {
                    it.copy(
                        cousin = value.toCousinUIState(),
                        selectedCousin = value[0].toCousinUIState(),
                    )
                }
                getMeals(value[0].id)

            },
            onError = {
                updateState { it.copy(error = it.error) }
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



    override fun onClickBack() {
        sendNewEffect(MealsScreenUIEffect.Back)
    }

    override fun onClickMeal() {
        sendNewEffect(MealsScreenUIEffect.NavigateToMealDetails)
    }

    override fun onClickCousinType(type: CousinUIState) {
        updateState { it.copy(selectedCousin = type) }
        getMeals(type.id)
    }
}
