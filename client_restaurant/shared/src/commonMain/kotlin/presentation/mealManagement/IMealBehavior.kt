package presentation.mealManagement

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

abstract class IMealBehavior : BaseScreenModel<MealEditorUIState, MealScreenUIEffect>(
    MealEditorUIState()
), MealScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope


    protected abstract suspend fun addMeal(): Boolean
    protected abstract suspend fun updateMeal(): Boolean

    override fun onAddMeal() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { addMeal() },
            ::onMealAddedSuccessfully,
            ::onError
        )
    }

    override fun onUpdateMeal() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            {updateMeal()},
            ::onMealAddedSuccessfully,
            ::onError
        )
    }

    override fun onCuisineClick() {
        state.value.sheetState.show()
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
        state.value.sheetState.dismiss()
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
        state.value.sheetState.dismiss()
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

    override fun onBackgroundClicked() {
        state.value.sheetState.dismiss()
        updateState { it.copy(isCuisinesShow = false) }
    }

}