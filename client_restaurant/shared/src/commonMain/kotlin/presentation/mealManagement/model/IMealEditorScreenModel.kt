package presentation.mealManagement.model

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cuisine
import domain.entity.Meal
import domain.usecase.IManageCuisineUseCase
import domain.usecase.IManageMealUseCase
import domain.usecase.IValidateManageMealUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.mealManagement.CuisineUIState
import presentation.mealManagement.IMealBehavior
import presentation.mealManagement.toMealAddition
import presentation.mealManagement.toMealUpdate
import presentation.mealManagement.toUIState


class IMealEditorScreenModel(private val mealId: String,
private val restaurantId:String) : IMealBehavior() {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    private val manageMeal: IManageMealUseCase by inject()
    private val cuisines: IManageCuisineUseCase by inject()
    private val restaurantMealValidation: IValidateManageMealUseCase by inject()


    init {
        getMeal()
    }

    override suspend fun addMeal(): Boolean {
        return manageMeal.addMeal(state.value.meal.toMealAddition(restaurantId))
    }

    override suspend fun updateMeal(): Boolean {
        val state = state.value.meal.toMealUpdate(mealId)
        val validationResult = restaurantMealValidation.isMealInformationValid(
            name = state.name,
            price = state.price,
            cuisines = state.cuisines,
            description = state.description,
        )
        if (validationResult) {
            return manageMeal.updateMeal(state)
        }
        return false
    }


    private fun getMeal() {
        updateState { it.copy(id = mealId, isLoading = true) }
        tryToExecute({ manageMeal.getMealById(mealId) }, ::onGetMealSuccess, ::onError)
    }

    private fun onGetMealSuccess(meal: Meal) {
        updateState { it.copy(isLoading = false, meal = meal.toUIState()) }
        tryToExecute({ cuisines.getCuisines() }, ::onGetCuisinesSuccess, ::onError)
    }

    private fun onGetCuisinesSuccess(cuisines: List<Cuisine>) {
        val mealCuisineIds = state.value.meal.mealCuisines.map { it.id }
        val cuisinesUi = cuisines.toUIState().onUpdateCuisineSelection(mealCuisineIds)
        val mealCuisines = cuisinesUi.filter { it.isSelected }
        updateState {
            it.copy(
                cuisines = cuisinesUi,
                isLoading = false,
                meal = it.meal.copy(mealCuisines = mealCuisines)
            )
        }
    }

    private fun List<CuisineUIState>.onUpdateCuisineSelection(cuisineIds: List<String>): List<CuisineUIState> {
        return map {
            if (it.id in cuisineIds) {
               val x= it.copy(isSelected = true)
                println("TEST $x")
                x
            } else {
                it
            }
        }
    }


}
