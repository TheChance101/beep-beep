package presentation.mealManagement

import presentation.base.BaseInteractionListener

interface MealScreenInteractionListener : BaseInteractionListener {
    fun onNameChange(name: String)
    fun onDescriptionChange(description: String)
    fun onPriceChange(price: String)
    fun onAddMeal()
    fun onUpdateMeal()
    fun onCuisineClick()
    fun onSaveCuisineClick()
    fun onCuisineSelected(id: String)
    fun onImagePicked(image: ByteArray)
    fun onClickBack()
    fun onCuisinesCancel()
    fun onBackgroundClicked()
}

