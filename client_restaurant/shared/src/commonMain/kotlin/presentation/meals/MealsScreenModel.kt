package presentation.meals

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cuisine
import domain.entity.Meal
import domain.usecase.IManageMealUseCase
import domain.usecase.IManageCuisineUseCase

import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class MealsScreenModel(
    private val restaurantId: String,
    private val mangeCousin: IManageCuisineUseCase,
    private val mangeMeal: IManageMealUseCase
) :
    BaseScreenModel<MealsScreenUIState, MealsScreenUIEffect>(MealsScreenUIState()),
    MealScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope


    init {
        getCuisine()
    }

    private fun getCuisine() {
        tryToExecute(
            function = {
                mangeCousin.getCuisineByRestaurantId(
                    restaurantId
                )
            },
            ::onGetCuisineSuccessfully,
            ::onError
        )
    }

    private fun getMeals(cuisineId: String) {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = {
                if (cuisineId.isEmpty()) {
                    mangeMeal.getAllMeals()
                } else {
                    mangeCousin.getMealsByCuisineId(cuisineId)
                }
            },
            ::onGetMealSuccessfully,
            ::onError
        )
    }

    private fun onGetCuisineSuccessfully(cuisines: List<Cuisine>) {
        updateState {
            it.copy(cuisines = cuisines.toCuisineUIState(), selectedCuisine = cuisines.toCuisineUIState()
                .first())
        }
        getMeals(state.value.selectedCuisine.id)
    }

    private fun onGetMealSuccessfully(meals: List<Meal>) {
        updateState { it.copy(meals = meals.toMealUIState(), isLoading = false) }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error, isLoading = false) }
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

    override fun onClickCuisineType(type: CuisineUIState) {
        updateState { it.copy(selectedCuisine = type) }
        getMeals(type.id)
    }
}
