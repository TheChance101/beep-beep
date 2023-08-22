package presentation.meal

import presentation.base.BaseInteractionListener

interface MealScreenInteractionListener : BaseInteractionListener {
    fun onImageClicked()
    fun onNameChanged(name: String)
    fun onDescriptionChanged(description: String)
    fun onPriceChanged(price: String)
    fun onClickAddMeal()
    fun onCuisineClick()
}

