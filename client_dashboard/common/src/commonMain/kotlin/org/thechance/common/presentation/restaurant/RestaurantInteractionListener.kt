package org.thechance.common.presentation.restaurant

import org.thechance.common.presentation.base.BaseInteractionListener

interface RestaurantInteractionListener : BaseInteractionListener, AddCuisineInteractionListener,
    AddRestaurantInteractionListener, FilterRestaurantsInteractionListener ,AddOfferInteractionListener{

    fun onSearchChange(restaurantName: String)

    fun onClickDropDownMenu()

    fun onDismissDropDownMenu()

    fun onPageClicked(pageNumber: Int)

    fun onItemPerPageChange(numberOfRestaurantsInPage: Int)

    fun onShowRestaurantMenu(restaurantId: String)

    fun onHideRestaurantMenu(restaurantId: String)

    fun onClickEditRestaurantMenuItem(restaurantId: String)

    fun onClickDeleteRestaurantMenuItem(id: String)

    fun onAddNewRestaurantClicked()

    fun onUpdateRestaurantClicked(restaurantId: String)

    fun onRetry()
}


interface AddCuisineInteractionListener {
  
    fun onClickAddCuisine()
    fun onClickCuisineImage()
    fun onSelectedCuisineImage(image: Any?)

    fun onClickDeleteCuisine(cuisineId: String)

    fun onCloseAddCuisineDialog()

    fun onClickCreateCuisine()
    fun onAddOfferClicked()

    fun onChangeCuisineName(cuisineName: String)
}

interface AddOfferInteractionListener {

    fun onClickAddOffer()
    fun onClickOfferImagePicker()
    fun onSelectedOfferImage(image: Any?)

    fun onClickDeleteOffer(cuisineId: String)

    fun onCloseAddOfferDialog()

    fun onClickCreateOffer()

    fun onChangeOfferName(offerName: String)
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

    fun onSaveFilterRestaurantsClicked(rating: Double, priceLevel: String)

    fun onCancelFilterRestaurantsClicked()

    fun onFilterClearAllClicked()
}