package presentation.meals

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cuisine
import domain.entity.CuisineWithMeals
import domain.entity.Meal
import domain.usecase.IManageMealUseCase
import domain.usecase.IManageCuisineUseCase

import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class MealsScreenModel(
    private val restaurantId: String,
    private val manageMeal: IManageMealUseCase
) : BaseScreenModel<MealsScreenUIState, MealsScreenUIEffect>(MealsScreenUIState()),
    MealScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getCuisine(restaurantId)
    }

    private fun getCuisine(restaurantId: String) {
        updateState { it.copy(isCuisinesLoading = true, isMealsLoading = true) }
        tryToExecute(
            function = { manageMeal.getCuisinesWithMealsInRestaurant(restaurantId) },
            ::onGetCuisineSuccessfully,
            ::onError
        )
    }

    private fun onGetCuisineSuccessfully(cuisines: List<CuisineWithMeals>) {
        updateState {
            it.copy(
                isCuisinesLoading = false,
                isMealsLoading = false,
                cuisines = cuisines.toCuisinesWithMealsUIState(),
                selectedCuisine = cuisines.toCuisinesWithMealsUIState().first()
            )
        }
        getAllMeals()
    }

    private fun getAllMeals() {
        updateState { mealsScreenUIState ->
            mealsScreenUIState.copy(
                meals = state.value.cuisines.flatMap { it.meals }.distinctBy { it.id },
                isMealsLoading = false
            )
        }
    }

    private fun getMealsByCuisineId(cuisineId: String) {
        val cuisine = state.value.cuisines.find { it.id == cuisineId }
        if (cuisine != null) {
            updateState { it.copy(meals = cuisine.meals) }
        }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error, isCuisinesLoading = false, isMealsLoading = false) }
    }

    override fun onClickBack() {
        sendNewEffect(MealsScreenUIEffect.Back)
    }

    override fun onClickMeal(mealId: String) {
        sendNewEffect(MealsScreenUIEffect.NavigateToMealDetails(mealId))
    }

    override fun onAddMeaClick() {
        sendNewEffect(MealsScreenUIEffect.NavigateToAddMeal(restaurantId))
    }

    override fun onClickCuisineType(cuisine: CuisineUIState, index: Int) {
        updateState { it.copy(selectedCuisine = cuisine) }
        if (index == 0) {
            getAllMeals()
        } else {
            getMealsByCuisineId(cuisine.id)
        }
    }

}