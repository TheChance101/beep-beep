package presentation.meal

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class MealScreenModel : BaseScreenModel<MealScreenUIState, MealScreenUIEffect>(MealScreenUIState()),
    MealScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    override fun onClickAddMeal() {
        TODO("Not yet implemented")
    }

    override fun onImageClicked() {
        TODO("Not yet implemented")
    }

    override fun onNameChanged(name: String) {
        TODO("Not yet implemented")
    }

    override fun onDescriptionChanged(description: String) {
        TODO("Not yet implemented")
    }

    override fun onPriceChanged(description: String) {
        TODO("Not yet implemented")
    }
}