package presentation.meal

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class MealScreenModel : BaseScreenModel<MealScreenUIState, MealScreenUIEffect>(MealScreenUIState()),
    MealScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    init {
        //e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a
    }

    override fun onClickAddMeal() {
        TODO("Not yet implemented")
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