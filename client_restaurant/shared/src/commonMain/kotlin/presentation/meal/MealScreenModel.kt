package presentation.meal

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Meal
import domain.usecase.IManageMealUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class MealScreenModel : BaseScreenModel<MealUIState, MealScreenUIEffect>(MealUIState()),
    MealScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    private val manageMeal: IManageMealUseCase by inject()

    private val mealId = "e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a"

    init {
        if (mealId.isNotEmpty()) {
            tryToExecute(
                { manageMeal.getMeal(mealId) },
                ::onGetMealSuccess,
                ::onAddMealError
            )
        }
    }

    override fun onClickAddMeal() {
        tryToExecute(
            { manageMeal.addMeal() },
            ::onMealAddedSuccessfully,
            ::onAddMealError
        )
    }

    private fun onMealAddedSuccessfully(result: Boolean) {

    }


    private fun onGetMealSuccess(meal: Meal) {
        updateState { meal.toMealUIState() }
    }

    private fun onAddMealError(error: ErrorState) {

    }

    override fun onCuisineClick() {
        TODO("Not yet implemented")
    }

    override fun onImageClicked() {
        TODO("Not yet implemented")
    }

    override fun onNameChanged(name: String) {
        updateState { it.copy(name = name) }
        updateState { it.copy(isAddEnable = it.isValid()) }
    }

    override fun onDescriptionChanged(description: String) {
        updateState { it.copy(description = description) }
        updateState { it.copy(isAddEnable = it.isValid()) }

    }

    override fun onPriceChanged(price: String) {
        updateState { it.copy(price = price) }
        updateState { it.copy(isAddEnable = it.isValid()) }
    }
}
