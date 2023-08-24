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

    fun onItemPerPageChange(numberOfItemsInPage: Int)
}
