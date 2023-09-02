package org.thechance.common.presentation.restaurant

import org.thechance.common.presentation.base.BaseInteractionListener

interface RestaurantInteractionListener : BaseInteractionListener {

    fun onSearchChange(restaurantName: String)

    fun onClickDropDownMenu()

    fun onDismissDropDownMenu()

    fun onClickFilterRatingBar(rating: Double)

    fun onClickFilterPriceBar(priceLevel: Int)

    fun onSaveFilterRestaurantsClicked(rating: Double, priceLevel: Int)

    fun onCancelFilterRestaurantsClicked()

    fun onPageClicked(pageNumber: Int)

    fun onItemPerPageChange(numberOfRestaurantsInPage: Int)

    fun onAddNewRestaurantClicked()

    fun onCancelCreateRestaurantClicked()

    fun onRestaurantNameChange(name: String)

    fun onOwnerUserNameChange(name: String)

    fun onPhoneNumberChange(number: String)

    fun onWorkingStartHourChange(hour: String)
    fun onWorkingEndHourChange(hour: String)

    fun onCreateNewRestaurantClicked()

    fun onAddressChange(address: String)
}
