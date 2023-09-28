package presentation.mealManagement.model

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cuisine
import domain.usecase.IManageCuisineUseCase
import domain.usecase.IManageMealUseCase
import domain.usecase.IValidateManageMealUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.ErrorState
import presentation.mealManagement.IMealBehavior
import presentation.mealManagement.MealScreenUIEffect
import presentation.mealManagement.toMealAddition
import presentation.mealManagement.toMealUpdate
import presentation.mealManagement.toUIState

class IMealCreationScreenModel(
private val restaurantId : String
) : IMealBehavior() {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    private val manageMeal: IManageMealUseCase by inject()
    private val cuisines: IManageCuisineUseCase by inject()
    private val restaurantMealValidation: IValidateManageMealUseCase by inject()

    init {
        getCuisines()
    }

    override suspend fun addMeal(): Boolean {
        val state = state.value.meal.toMealAddition(restaurantId)
        val validationResult = restaurantMealValidation.isMealInformationValid(
            name = state.name,
            price = state.price,
            cuisines = state.cuisines,
            description = state.description,
        )
        if (validationResult) {
            return manageMeal.addMeal(state)
        }
        return false
    }

    override suspend fun updateMeal(): Boolean {
        return manageMeal.updateMeal(state.value.meal.toMealUpdate(mealId = ""))
    }

    fun getCuisines() {
        tryToExecute({ cuisines.getCuisines() }, ::onGetCuisinesSuccess, ::onAddCuisinesError)
    }

    private fun onGetCuisinesSuccess(cuisines: List<Cuisine>) {
        updateState { it.copy(cuisines = cuisines.toUIState(), isLoading = false) }
    }

    private fun onAddCuisinesError(error: ErrorState) {
        updateState { it.copy(isLoading = false, error = error.toString()) }
        sendNewEffect(MealScreenUIEffect.MealResponseFailed(error.toString()))
    }

}
