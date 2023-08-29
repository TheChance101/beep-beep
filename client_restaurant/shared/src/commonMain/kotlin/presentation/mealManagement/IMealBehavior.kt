package presentation.mealManagement

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cuisine
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

abstract class IMealBehavior : BaseScreenModel<MealEditorUIState, MealScreenUIEffect>(
    MealEditorUIState()
), MealScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    init {
        getCuisines()
    }

    protected abstract suspend fun addMeal(): Boolean
    protected abstract suspend fun getCuisiness(): List<Cuisine>

    override fun onAddMeal() {
        tryToExecute(
            { addMeal() },
            ::onMealAddedSuccessfully,
            ::onError
        )
    }

    override fun onCuisineClick() {
        updateState { it.copy(isCuisinesShow = true) }
    }

    private fun onMealAddedSuccessfully(result: Boolean) {
        sendNewEffect(MealScreenUIEffect.MealResponseSuccessfully)
    }

    protected fun onError(error: ErrorState) {
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

    fun getCuisines() {
        tryToExecute({ getCuisiness() }, ::onGetCuisinesSuccess, ::onAddCuisinesError)
    }

    private fun onGetCuisinesSuccess(cuisines: List<Cuisine>) {
        updateState { it.copy(cuisines = cuisines.toUIState(), isLoading = false) }
    }

    private fun onAddCuisinesError(error: ErrorState) {
        updateState { it.copy(isLoading = false, error = error.toString()) }
        sendNewEffect(MealScreenUIEffect.MealResponseFailed(error.toString()))
    }

}