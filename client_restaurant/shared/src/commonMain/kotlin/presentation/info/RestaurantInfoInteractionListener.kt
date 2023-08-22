package presentation.info

import presentation.base.BaseInteractionListener

interface RestaurantInfoInteractionListener: BaseInteractionListener {
    fun onRestaurantNameChanged()
    fun onPhoneNumberChanged()
    fun onStartWorkingHourChanged()
    fun onEndWorkingHourChanged()
    fun onDescriptionChanged()
    fun onClickSave()
    fun onClickLogout()
}