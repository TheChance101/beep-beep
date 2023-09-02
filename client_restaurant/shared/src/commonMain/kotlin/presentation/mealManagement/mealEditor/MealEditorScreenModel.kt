package presentation.mealManagement.mealEditor

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cuisine
import domain.entity.Meal
import domain.usecase.IManageMealUseCase
import domain.usecase.IManageCuisineUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.mealManagement.CuisineUIState
import presentation.mealManagement.MealScreenInteractionListener
import presentation.mealManagement.MealScreenUIEffect
import presentation.mealManagement.toMealAddition
import presentation.mealManagement.toUIState


class MealEditorScreenModel(private val mealId: String) :
    BaseScreenModel<MealEditorUIState, MealScreenUIEffect>(MealEditorUIState()),
    MealScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    private val manageMeal: IManageMealUseCase by inject()
    private val cuisines: IManageCuisineUseCase by inject()

    init {
        getMeal()
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

    override fun onAddMeal() {
        tryToExecute(
            { manageMeal.addMeal(state.value.meal.toMealAddition()) },
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


    private fun onError(error: ErrorState) {
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
        updateState { it.copy(cuisines = it.cuisines.onUpdateCuisineSelection(listOf(id))) }
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
