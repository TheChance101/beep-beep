package presentation.meal

import presentation.base.BaseInteractionListener

interface MealScreenInteractionListener : BaseInteractionListener {
    fun onImageClick()
    fun onNameChange(name: String)
    fun onDescriptionChange(description: String)
    fun onPriceChange(price: String)
    fun onAddMeal()
    fun onCuisineClick()
    fun onSaveCuisineClick()
    fun onCuisineSelected(id: String)

}

