package presentation.meals

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IGetCuisineUseCase
import domain.usecase.IGetMealsByCousin
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.meals.state.CousinUIState
import presentation.meals.state.MealsScreenUIState
import presentation.meals.state.toCousinUIState
import presentation.meals.state.toUIState

class MealsScreenModel :
    BaseScreenModel<MealsScreenUIState, MealsScreenUIEffect>(MealsScreenUIState()),
    MealScreenInteractionListener {
    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    private val getCuisines: IGetCuisineUseCase by inject()
    private val getMealsUseCase: IGetMealsByCousin by inject()

    init {
        getCousin()
    }

    private fun getCousin() {
        tryToExecute(
            function = {
                getCuisines.getRestaurantCuisines("1")

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

    override fun onClickMeal(mealId: String) {
        sendNewEffect(MealsScreenUIEffect.NavigateToMealDetails(mealId))
    }

    override fun onAddMeaClick() {
        sendNewEffect(MealsScreenUIEffect.NavigateToAddMeal)
    }

    override fun onClickCousinType(type: CousinUIState) {
        updateState { it.copy(selectedCousin = type) }
        getMeals(type.id)
    }
}
