package presentation.meal

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cuisine
import domain.entity.Meal
import domain.usecase.IGetCuisineUseCase
import domain.usecase.IManageMealUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class MealScreenModel(private val mealId: String?) :
    BaseScreenModel<MealUIState, MealScreenUIEffect>(MealUIState()), MealScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    private val manageMeal: IManageMealUseCase by inject()
    private val cuisines: IGetCuisineUseCase by inject()

    init {
        tryToExecute({ cuisines.getCuisines() }, ::onGetCuisinesSuccess, ::onAddMealError)

        mealId?.let {
            tryToExecute({ manageMeal.getMeal(mealId) }, ::onGetMealSuccess, ::onAddMealError)
        }
    }

    private fun onGetCuisinesSuccess(cuisines: List<Cuisine>) {
        updateState { state ->
            val selectedCuisineIds = state.selectedCuisines.map { it.id }
            val updatedCuisines = cuisines.toCuisineUIState().map { cuisine ->
                cuisine.copy(isSelected = cuisine.id in selectedCuisineIds)
            }
            state.copy(cuisines = updatedCuisines)
        }
    }

    override fun onAddMeal() {
        tryToExecute(
            { manageMeal.addMeal(state.value.toMealEntity()) },
            ::onMealAddedSuccessfully,
            ::onAddMealError
        )
    }

    override fun onCuisineClick() {
        updateState { state ->
            val selectedCuisineIds = state.selectedCuisines.map { it.id }
            val cuisines = state.cuisines.map {
                if (it.id in selectedCuisineIds) {
                    it.copy(isSelected = true)
                }
                it
            }
            state.copy(cuisines = cuisines)
        }
    }

    private fun onMealAddedSuccessfully(result: Boolean) {
        sendNewEffect(MealScreenUIEffect.MealResponseSuccessfully)
    }


    private fun onGetMealSuccess(meal: Meal) {
        updateState { meal.toMealUIState() }
        updateState { it.copy(isAddEnable = it.isValid()) }
    }

    private fun onAddMealError(error: ErrorState) {
        TODO("Not yet implemented")
    }


    override fun onSaveCuisineClick() {
        updateState { it.copy(selectedCuisines = it.cuisines.filter { it.isSelected }) }
        updateState { it.copy(isAddEnable = it.isValid()) }
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
        updateState { it.copy(image = image) }
    }

    override fun onClickBack() {
        sendNewEffect(MealScreenUIEffect.Back)
    }

    override fun onNameChange(name: String) {
        updateState { it.copy(name = name) }
        updateState { it.copy(isAddEnable = it.isValid()) }
    }

    override fun onDescriptionChange(description: String) {
        updateState { it.copy(description = description) }
        updateState { it.copy(isAddEnable = it.isValid()) }

    }

    override fun onPriceChange(price: String) {
        updateState { it.copy(price = price) }
        updateState { it.copy(isAddEnable = it.isValid()) }
    }
}
