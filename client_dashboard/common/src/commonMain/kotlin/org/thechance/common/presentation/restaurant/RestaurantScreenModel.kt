package org.thechance.common.presentation.restaurant

import org.thechance.common.domain.entity.Location
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.usecase.FilterRestaurantsUseCase
import org.thechance.common.domain.usecase.IHandleLocationUseCase
import org.thechance.common.domain.usecase.IManageRestaurantUseCase
import org.thechance.common.domain.usecase.SearchFilterRestaurantsUseCase
import org.thechance.common.domain.usecase.SearchRestaurantsByRestaurantNameUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState
import kotlin.math.ceil


class RestaurantScreenModel(
    private val manageRestaurant: IManageRestaurantUseCase,
    private val handleLocation: IHandleLocationUseCase,
    private val searchRestaurants: SearchRestaurantsByRestaurantNameUseCase,
    private val searchFilterRestaurants: SearchFilterRestaurantsUseCase,
    private val filterRestaurants: FilterRestaurantsUseCase,
) : BaseScreenModel<RestaurantUiState, RestaurantUIEffect>(RestaurantUiState()),
    RestaurantInteractionListener {

    init {
        getRestaurants()
    }

    private fun getRestaurants() {
        tryToExecute(manageRestaurant::getRestaurant, ::onGetRestaurantSuccessfully, ::onError)
    }

    private fun onGetRestaurantSuccessfully(restaurant: List<Restaurant>) {
        updateState {
            it.copy(
                restaurants = restaurant.toUiState(),
                isLoading = false,
                numberOfRestaurants = restaurant.size,
                maxPageCount = ceil(restaurant.size.toDouble() / it.numberOfItemsInPage).toInt()
            )
        }
    }

    private fun searchRestaurants(restaurantName: String) {
        tryToExecute(
            { searchRestaurants.invoke(restaurantName) }, ::onGetRestaurantSuccessfully, ::onError
        )
    }

    private fun filterRestaurants(rating: Double, priceLevel: Int) {
        tryToExecute(
            { filterRestaurants.invoke(rating, priceLevel) },
            ::onGetRestaurantSuccessfully,
            ::onError
        )
    }

    private fun searchFilterRestaurants(restaurantName: String, rating: Double, priceLevel: Int) {
        tryToExecute(
            { searchFilterRestaurants.invoke(restaurantName, rating, priceLevel) },
            ::onGetRestaurantSuccessfully,
            ::onError
        )
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error, isLoading = false) }
    }


    override fun onSaveFilterRestaurantsClicked(rating: Double, priceLevel: Int) {
        updateState { it.copy(isFiltered = true) }
        filterRestaurants(rating = rating, priceLevel = priceLevel)
    }

    override fun onCancelFilterRestaurantsClicked() {
        updateState { it.copy(filterRating = 0.0, filterPriceLevel = 0, isFiltered = false) }
        getRestaurants()
    }


    override fun onSearchChange(restaurantName: String) {
        updateState { it.copy(search = restaurantName) }
        if (state.value.isFiltered) searchFilterRestaurants(
            restaurantName = restaurantName,
            rating = state.value.filterRating,
            priceLevel = state.value.filterPriceLevel
        ) else searchRestaurants(restaurantName)

    }

    override fun onClickDropDownMenu() {
        updateState { it.copy(isFilterDropdownMenuExpanded = true) }
    }

    override fun onDismissDropDownMenu() {
        updateState { it.copy(isFilterDropdownMenuExpanded = false) }
    }

    override fun onClickFilterRatingBar(rating: Double) {
        updateState { it.copy(filterRating = rating) }
    }

    override fun onClickFilterPriceBar(priceLevel: Int) {
        updateState { it.copy(filterPriceLevel = priceLevel) }
    }

    override fun onPageClicked(pageNumber: Int) {
        updateState { it.copy(selectedPageNumber = pageNumber) }
    }

    override fun onItemPerPageChange(numberOfItemsInPage: Int) {
        val numberOfItems = when {
            numberOfItemsInPage < 1 -> 1
            numberOfItemsInPage > 100 -> 100
            else -> numberOfItemsInPage

        }
        updateState {
            it.copy(
                numberOfItemsInPage = numberOfItems,
                maxPageCount = ceil(it.numberOfRestaurants.toDouble() / numberOfItems).toInt()
            )
        }
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
