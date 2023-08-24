package presentation.info

import presentation.base.BaseInteractionListener

interface RestaurantInfoInteractionListener: BaseInteractionListener {
    fun onRestaurantNameChange(name: String)
    fun onPhoneNumberChange(phoneNum: String)
    fun onOpeningTimeChange(openingTime: String)
    fun onClosingTimeChange(closingTime: String)
    fun onDescriptionChanged(description: String)
    fun onClickSave()
    fun onClickLogout()
    fun onClickBackArrow()
}