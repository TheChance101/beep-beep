package presentation.mealManagement.mealCreation

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cuisine
import domain.usecase.IManageMealUseCase
import domain.usecase.IManageCuisineUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.mealManagement.MealScreenInteractionListener
import presentation.mealManagement.MealScreenUIEffect
import presentation.mealManagement.toMealAddition
import presentation.mealManagement.toUIState

class MealCreationScreenModel :
    BaseScreenModel<MealCreationUIState, MealScreenUIEffect>(MealCreationUIState()),
    MealScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    private val manageMeal: IManageMealUseCase by inject()
    private val cuisines: IManageCuisineUseCase by inject()

    init {
        getCuisines()
    }

    private fun getCuisines() {
        updateState { it.copy(isLoading = true) }
        tryToExecute({ cuisines.getCuisines() }, ::onGetCuisinesSuccess, ::onAddMealError)
    }

    private fun onGetCuisinesSuccess(cuisines: List<Cuisine>) {
        updateState { it.copy(cuisines = cuisines.toUIState(), isLoading = false) }
    }

    override fun onAddMeal() {
        tryToExecute(
            { manageMeal.addMeal(state.value.meal.toMealAddition()) },
            ::onMealAddedSuccessfully,
            ::onAddMealError
        )
    }

    override fun onCuisineClick() {
        updateState { it.copy(isCuisinesShow = true) }
    }

    private fun onMealAddedSuccessfully(result: Boolean) {
        sendNewEffect(MealScreenUIEffect.MealResponseSuccessfully)
    }


    private fun onAddMealError(error: ErrorState) {
        updateState { it.copy(isLoading = false, error = error.toString()) }
        sendNewEffect(MealScreenUIEffect.MealResponseFailed(error.toString()))
    }


    override fun onSaveCuisineClick() {
        val mealCuisines = state.value.cuisines.filter { it.isSelected }
        updateState {
            it.copy(
                meal = it.meal.copy(mealCuisines = mealCuisines),
                isCuisinesShow = false
            )
        }
    }

    override fun onCuisineSelected(id: String) {
        updateState { state ->
            val updatedCuisines = state.cuisines.map { cuisine ->
                if (cuisine.id == id) {
                    cuisine.copy(isSelected = !cuisine.isSelected)
                } else {
                    cuisine
                }
            }
            state.copy(cuisines = updatedCuisines)
        }
    }

    override fun onImagePicked(image: ByteArray) {
        updateState { it.copy(meal = it.meal.copy(image = image)) }
    }

    override fun onClickBack() {
        sendNewEffect(MealScreenUIEffect.Back)
    }

    override fun onCuisinesCancel() {
        updateState { it.copy(isCuisinesShow = false) }
    }

    override fun onNameChange(name: String) {
        updateState { it.copy(meal = it.meal.copy(name = name)) }
    }

    override fun onDescriptionChange(description: String) {
        updateState { it.copy(meal = it.meal.copy(description = description)) }

    }

    override fun onPriceChange(price: String) {
        updateState { it.copy(meal = it.meal.copy(price = price)) }
    }
}
