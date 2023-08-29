package presentation.mealManagement.model

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cuisine
import domain.usecase.IManageMealUseCase
import domain.usecase.IMangeCuisineUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.ErrorState
import presentation.mealManagement.IMealBehavior
import presentation.mealManagement.MealScreenUIEffect
import presentation.mealManagement.toMealAddition
import presentation.mealManagement.toUIState

class IMealCreationScreenModel : IMealBehavior() {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    private val manageMeal: IManageMealUseCase by inject()
    private val cuisines: IMangeCuisineUseCase by inject()


    init {
        getCuisines()
    }


    override suspend fun addMeal(): Boolean {
        return manageMeal.addMeal(state.value.meal.toMealAddition())
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
