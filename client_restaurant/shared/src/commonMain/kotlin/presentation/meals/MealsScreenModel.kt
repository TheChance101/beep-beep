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
    private val manageCousin: IManageCuisineUseCase,
    private val manageMeal: IManageMealUseCase
) : BaseScreenModel<MealsScreenUIState, MealsScreenUIEffect>(MealsScreenUIState()),
    MealScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getCuisine()
    }

    private fun getCuisine() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = { manageCousin.getCuisines() },
            ::onGetCuisineSuccessfully,
            ::onError
        )
    }

    private fun onGetCuisineSuccessfully(cuisines: List<Cuisine>) {
        updateState {
            it.copy(isLoading = false,
                cuisines = cuisines.toCuisineUIState(),
                selectedCuisine = cuisines.toCuisineUIState().first()
            )
        }
        getMeals(restaurantId)
    }

    private fun getMeals(restaurantId: String) {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = { manageMeal.getAllMeals(restaurantId,1,20) },
            ::onGetMealSuccessfully,
            ::onError
        )
    }

    private fun onGetMealSuccessfully(meals: List<Meal>) {
        updateState { it.copy(meals = meals.toMealUIState(), isLoading = false) }
    }

    private fun getMealsByCuisineId(cuisineId: String) {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { manageMeal.getMealsByCuisineId(cuisineId, 1, 20) },
            ::onGetMealsByCuisineIdSuccessfully,
            ::onError
        )
    }

    private fun onGetMealsByCuisineIdSuccessfully(meals: List<Meal>) {
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
        sendNewEffect(MealsScreenUIEffect.NavigateToAddMeal(restaurantId))
    }

    override fun onClickCuisineType(cuisine: CuisineUIState, index: Int) {
        updateState { it.copy(selectedCuisine = cuisine) }
        if (index == 0) {
            getMeals(restaurantId)
        } else {
            getMealsByCuisineId(cuisine.id)
        }
    }

}