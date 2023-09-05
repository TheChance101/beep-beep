package org.thechance.common.presentation.restaurant

import org.thechance.common.presentation.base.BaseInteractionListener

interface RestaurantInteractionListener : BaseInteractionListener, AddCuisineInteractionListener,
    AddRestaurantInteractionListener, FilterRestaurantsInteractionListener {

    fun onSearchChange(restaurantName: String)

    fun onClickDropDownMenu()

    fun onDismissDropDownMenu()

    fun onPageClicked(pageNumber: Int)

    fun onItemPerPageChange(numberOfRestaurantsInPage: Int)

    fun showEditRestaurantMenu(restaurantName: String)

    fun hideEditRestaurantMenu()

    fun onClickEditRestaurantMenuItem(restaurant: RestaurantUiState.RestaurantDetailsUiState)

    fun onClickDeleteRestaurantMenuItem(restaurant: RestaurantUiState.RestaurantDetailsUiState)

    fun onAddNewRestaurantClicked()
}


interface AddCuisineInteractionListener {
  
    fun onClickAddCuisine()

    fun onClickDeleteCuisine(cuisineName: String)

    fun onCloseAddCuisineDialog()

    fun onClickCreateCuisine()

    fun onChangeCuisineName(cuisineName: String)
}

interface AddRestaurantInteractionListener {

    fun onCancelCreateRestaurantClicked()

    fun onRestaurantNameChange(name: String)

    fun onOwnerUserNameChange(name: String)

    fun onPhoneNumberChange(number: String)

    fun onWorkingStartHourChange(hour: String)

    fun onWorkingEndHourChange(hour: String)

    fun onCreateNewRestaurantClicked()

    fun onLocationChange(location: String)
}

interface FilterRestaurantsInteractionListener {

    fun onClickFilterRatingBar(rating: Double)

    fun onClickFilterPriceBar(priceLevel: Int)

    fun onSaveFilterRestaurantsClicked(rating: Double, priceLevel: Int)

    fun onCancelFilterRestaurantsClicked()

    fun onFilterClearAllClicked()
}