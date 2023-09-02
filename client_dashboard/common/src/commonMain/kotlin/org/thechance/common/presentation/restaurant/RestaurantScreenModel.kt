package org.thechance.common.presentation.restaurant

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Location
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.usecase.IManageLocationUseCase
import org.thechance.common.domain.usecase.IManageRestaurantUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState


class RestaurantScreenModel(
    private val manageRestaurant: IManageRestaurantUseCase,
    private val handleLocation: IManageLocationUseCase
) : BaseScreenModel<RestaurantUiState, RestaurantUIEffect>(RestaurantUiState()),
    RestaurantInteractionListener {

    init {
        getRestaurants(
            state.value.selectedPageNumber,
            state.value.numberOfRestaurantsInPage,
            state.value.search,
            null,
            null
        )
    }

    private fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double?,
        priceLevel: Int?
    ) {
        tryToExecute(
            {
                manageRestaurant.getRestaurant(
                    pageNumber,
                    numberOfRestaurantsInPage,
                    restaurantName,
                    rating,
                    priceLevel
                )
            },
            ::onGetRestaurantSuccessfully,
            ::onError
        )
    }

    private fun onGetRestaurantSuccessfully(restaurant: DataWrapper<Restaurant>) {
        updateState {
            it.copy(
                restaurants = restaurant.result.toUiState(),
                isLoading = false,
                numberOfRestaurants = restaurant.numberOfResult,
                maxPageCount = restaurant.totalPages
            )
        }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error, isLoading = false) }
    }


    override fun onSaveFilterRestaurantsClicked(rating: Double, priceLevel: Int) {
        updateState {
            it.copy(
                restaurantFilterDropdownMenuUiState = it.restaurantFilterDropdownMenuUiState.copy(
                    isFiltered = true
                )
            )
        }
        getRestaurants(
            pageNumber = 1,
            numberOfRestaurantsInPage = state.value.numberOfRestaurantsInPage,
            rating = rating,
            priceLevel = priceLevel,
            restaurantName = state.value.search
        )
    }

    override fun onCancelFilterRestaurantsClicked() {
        updateState {
            it.copy(
                restaurantFilterDropdownMenuUiState = it.restaurantFilterDropdownMenuUiState.copy(
                    filterRating = 0.0,
                    filterPriceLevel = 1,
                    isFiltered = false
                )
            )
        }
        getRestaurants(
            pageNumber = 1,
            numberOfRestaurantsInPage = state.value.numberOfRestaurantsInPage,
            rating = null,
            priceLevel = null,
            restaurantName = state.value.search
        )
    }


    override fun onSearchChange(restaurantName: String) {
        updateState { it.copy(search = restaurantName) }
        getRestaurants(
            pageNumber = 1,
            numberOfRestaurantsInPage = state.value.numberOfRestaurantsInPage,
            rating = if (state.value.restaurantFilterDropdownMenuUiState.isFiltered) state.value.restaurantFilterDropdownMenuUiState.filterRating else null,
            priceLevel = if (state.value.restaurantFilterDropdownMenuUiState.isFiltered) state.value.restaurantFilterDropdownMenuUiState.filterPriceLevel else null,
            restaurantName = state.value.search
        )

    }

    override fun onClickDropDownMenu() {
        updateState {
            it.copy(
                restaurantFilterDropdownMenuUiState = it.restaurantFilterDropdownMenuUiState.copy(
                    isFilterDropdownMenuExpanded = true
                )
            )
        }
    }

    override fun onDismissDropDownMenu() {
        updateState {
            it.copy(
                restaurantFilterDropdownMenuUiState = it.restaurantFilterDropdownMenuUiState.copy(
                    isFilterDropdownMenuExpanded = false
                )
            )
        }
    }

    override fun onClickFilterRatingBar(rating: Double) {
        updateState {
            it.copy(
                restaurantFilterDropdownMenuUiState = it.restaurantFilterDropdownMenuUiState.copy(
                    filterRating = rating
                )
            )
        }
    }

    override fun onClickFilterPriceBar(priceLevel: Int) {
        updateState {
            it.copy(
                restaurantFilterDropdownMenuUiState = it.restaurantFilterDropdownMenuUiState.copy(
                    filterPriceLevel = priceLevel
                )
            )
        }
    }

    override fun onPageClicked(pageNumber: Int) {
        updateState { it.copy(selectedPageNumber = pageNumber) }
        getRestaurants(
            pageNumber = pageNumber,
            numberOfRestaurantsInPage = state.value.numberOfRestaurantsInPage,
            rating = if (state.value.restaurantFilterDropdownMenuUiState.isFiltered) state.value.restaurantFilterDropdownMenuUiState.filterRating else null,
            priceLevel = if (state.value.restaurantFilterDropdownMenuUiState.isFiltered) state.value.restaurantFilterDropdownMenuUiState.filterPriceLevel else null,
            restaurantName = state.value.search
        )
    }

    override fun onItemPerPageChange(numberOfRestaurantsInPage: Int) {
        updateState { it.copy(numberOfRestaurantsInPage = numberOfRestaurantsInPage) }
        getRestaurants(
            pageNumber = state.value.selectedPageNumber,
            numberOfRestaurantsInPage = numberOfRestaurantsInPage,
            rating = if (state.value.restaurantFilterDropdownMenuUiState.isFiltered) state.value.restaurantFilterDropdownMenuUiState.filterRating else null,
            priceLevel = if (state.value.restaurantFilterDropdownMenuUiState.isFiltered) state.value.restaurantFilterDropdownMenuUiState.filterPriceLevel else null,
            restaurantName = state.value.search
        )
    }

    override fun onAddNewRestaurantClicked() {
        updateState { it.copy(isAddNewRestaurantDialogVisible = true) }
        if (state.value.addNewRestaurantDialogUiState.currentLocation.isEmpty())
            getCurrentLocation()
    }

    private fun getCurrentLocation() {
        tryToExecute(
            callee = { handleLocation.getCurrentLocation() },
            onSuccess = ::onGetCurrentLocationSuccess,
            onError = ::onError,
        )
    }

    private fun onGetCurrentLocationSuccess(location: Location) {
        updateState {
            it.copy(
                addNewRestaurantDialogUiState = it.addNewRestaurantDialogUiState.copy(
                    currentLocation = location.location
                )
            )
        }
    }

    override fun onCancelCreateRestaurantClicked() {
        updateState { it.copy(isAddNewRestaurantDialogVisible = false) }
    }

    override fun onRestaurantNameChange(name: String) {
        updateState {
            it.copy(
                addNewRestaurantDialogUiState = it.addNewRestaurantDialogUiState.copy(
                    name = name
                )
            )
        }
    }

    override fun onOwnerUserNameChange(name: String) {
        updateState {
            it.copy(
                addNewRestaurantDialogUiState = it.addNewRestaurantDialogUiState.copy(
                    ownerUsername = name
                )
            )
        }
    }

    override fun onPhoneNumberChange(number: String) {
        updateState {
            it.copy(
                addNewRestaurantDialogUiState = it.addNewRestaurantDialogUiState.copy(
                    phoneNumber = number
                )
            )
        }
    }

    override fun onWorkingStartHourChange(hour: String) {
        updateState {
            it.copy(
                addNewRestaurantDialogUiState = it.addNewRestaurantDialogUiState.copy(
                    startTime = hour
                )
            )
        }
    }

    override fun onWorkingEndHourChange(hour: String) {
        updateState {
            it.copy(
                addNewRestaurantDialogUiState = it.addNewRestaurantDialogUiState.copy(
                    endTime = hour
                )
            )
        }
    }

    override fun onAddressChange(address: String) {
        updateState {
            it.copy(
                addNewRestaurantDialogUiState = it.addNewRestaurantDialogUiState.copy(
                    location = address
                )
            )
        }
    }

    override fun onCreateNewRestaurantClicked() {
        tryToExecute(
            callee = { manageRestaurant.createRestaurant(mutableState.value.addNewRestaurantDialogUiState.toEntity()) },
            onSuccess = ::onCreateRestaurantSuccessfully,
            onError = ::onError,
        )
    }

    private fun onCreateRestaurantSuccessfully(restaurant: Restaurant) {
        val newRestaurant =
            mutableState.value.restaurants.toMutableList().apply { add(restaurant.toUiState()) }
        updateState {
            it.copy(
                restaurants = newRestaurant,
                isLoading = false,
                isAddNewRestaurantDialogVisible = false
            )
        }
    }
}
