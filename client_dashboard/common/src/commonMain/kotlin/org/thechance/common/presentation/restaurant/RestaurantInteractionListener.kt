package org.thechance.common.presentation.restaurant

import org.thechance.common.presentation.base.BaseInteractionListener

interface RestaurantInteractionListener : BaseInteractionListener {

    fun onSearchChange(restaurantName: String)

    fun onClickDropDownMenu()

    fun onDismissDropDownMenu()

    fun onClickFilterRating(rating: Double)

    fun onClickFilterPrice(priceLevel: Int)

    fun onPageClicked(pageNumber: Int)

    fun onItemPerPageChange(numberOfItemsInPage: Int)
}
