package org.thechance.common.presentation.restaurant

import org.thechance.common.presentation.base.BaseInteractionListener

interface RestaurantInteractionListener : BaseInteractionListener {

    fun onSearchChange(text: String)

    fun onClickDropDownMenu()

    fun onDismissDropDownMenu()

    fun onClickFilterRating(rating: Double)

    fun onClickFilterPrice(priceLevel: Int)

    fun onPageClicked(pageNumber: Int)

    fun onItemPerPageChange(numberOfItemsInPage: Int)

    fun onAddNewRestaurantClicked()

    fun onCancelCreateRestaurantClicked()

    fun onRestaurantNameChange(name: String)

    fun onOwnerUserNameChange(name: String)

    fun onPhoneNumberChange(number: String)

    fun onWorkingHourChange(hour: String)

}
