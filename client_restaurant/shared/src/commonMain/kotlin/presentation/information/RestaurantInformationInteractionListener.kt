package presentation.information

import presentation.base.BaseInteractionListener

interface RestaurantInformationInteractionListener: BaseInteractionListener {
    fun onRestaurantNameChange(name: String)
    fun onPhoneNumberChange(phoneNum: String)
    fun onOpeningTimeChange(openingTime: String)
    fun onClosingTimeChange(closingTime: String)
    fun onDescriptionChanged(description: String)
    fun onClickSave()
    fun onClickLogout()
    fun onClickBackArrow()
    fun onImagePicked(image: ByteArray)
}