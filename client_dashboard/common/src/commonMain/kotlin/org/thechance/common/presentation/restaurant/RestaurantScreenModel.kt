package org.thechance.common.presentation.restaurant

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Location
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.usecase.IFilterRestaurantsUseCase
import org.thechance.common.domain.usecase.IHandleLocationUseCase
import org.thechance.common.domain.usecase.IManageRestaurantUseCase
import org.thechance.common.domain.usecase.IManageSearchRestaurantsUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState


class RestaurantScreenModel(
    private val manageRestaurant: IManageRestaurantUseCase,
    private val handleLocation: IHandleLocationUseCase,
    private val searchRestaurants: IManageSearchRestaurantsUseCase,
    private val filterRestaurants: IFilterRestaurantsUseCase,
) : BaseScreenModel<RestaurantUiState, RestaurantUIEffect>(RestaurantUiState()),
    RestaurantInteractionListener {

    init {
        getRestaurants(state.value.selectedPageNumber, state.value.numberOfRestaurantInPage)
    }

    private fun getRestaurants(pageNumber: Int, numberOfRestaurantInPage: Int) {
        tryToExecute(
            { manageRestaurant.getRestaurant(pageNumber, numberOfRestaurantInPage) },
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

    private fun searchRestaurants(
        restaurantName: String,
        pageNumber: Int,
        numberOfRestaurantInPage: Int
    ) {
        tryToExecute(
            {
                searchRestaurants.searchRestaurantsByRestaurantName(
                    restaurantName,
                    pageNumber,
                    numberOfRestaurantInPage
                )
            },
            ::onGetRestaurantSuccessfully,
            ::onError
        )
    }

    private fun filterRestaurants(
        rating: Double,
        priceLevel: Int,
        pageNumber: Int,
        numberOfRestaurantInPage: Int
    ) {
        tryToExecute(
            { filterRestaurants.invoke(rating, priceLevel, pageNumber, numberOfRestaurantInPage) },
            ::onGetRestaurantSuccessfully,
            ::onError
        )
    }

    private fun searchFilteredRestaurants(
        restaurantName: String,
        rating: Double,
        priceLevel: Int,
        pageNumber: Int,
        numberOfRestaurantInPage: Int
    ) {
        tryToExecute(
            {
                searchRestaurants.searchFilteredRestaurantsByName(
                    restaurantName,
                    rating,
                    priceLevel,
                    pageNumber,
                    numberOfRestaurantInPage
                )
            },
            ::onGetRestaurantSuccessfully,
            ::onError
        )
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error, isLoading = false) }
    }


    override fun onSaveFilterRestaurantsClicked(rating: Double, priceLevel: Int) {
        updateState { it.copy(isFiltered = true) }
        filterRestaurants(
            rating = rating,
            priceLevel = priceLevel,
            pageNumber = 1,
            numberOfRestaurantInPage = state.value.numberOfRestaurantInPage
        )
    }

    override fun onCancelFilterRestaurantsClicked() {
        updateState { it.copy(filterRating = 0.0, filterPriceLevel = 0, isFiltered = false) }
        getRestaurants(
            pageNumber = 1,
            numberOfRestaurantInPage = state.value.numberOfRestaurantInPage
        )
    }


    override fun onSearchChange(restaurantName: String) {
        updateState { it.copy(search = restaurantName, isSearch = restaurantName.isNotEmpty()) }
        if (state.value.isFiltered) searchFilteredRestaurants(
            restaurantName = restaurantName,
            rating = state.value.filterRating,
            priceLevel = state.value.filterPriceLevel,
            pageNumber = 1,
            numberOfRestaurantInPage = state.value.numberOfRestaurantInPage
        ) else searchRestaurants(
            restaurantName,
            pageNumber = 1,
            numberOfRestaurantInPage = state.value.numberOfRestaurantInPage
        )

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
        when {
            state.value.isSearch && state.value.isFiltered -> searchFilteredRestaurants(
                state.value.search,
                state.value.filterRating,
                state.value.filterPriceLevel,
                pageNumber,
                state.value.numberOfRestaurants
            )

            state.value.isSearch -> searchRestaurants(
                state.value.search,
                pageNumber,
                state.value.numberOfRestaurants
            )

            state.value.isFiltered -> filterRestaurants(
                state.value.filterRating,
                state.value.filterPriceLevel,
                pageNumber,
                state.value.numberOfRestaurants
            )

            else -> getRestaurants(
                pageNumber,
                state.value.numberOfRestaurants
            )
        }
    }

    override fun onItemPerPageChange(numberOfRestaurantInPage: Int) {
        updateState {
            it.copy(
                numberOfRestaurantInPage = numberOfRestaurantInPage,
                maxPageCount = state.value.maxPageCount
            )
        }
        when {
            state.value.isSearch && state.value.isFiltered -> searchFilteredRestaurants(
                state.value.search,
                state.value.filterRating,
                state.value.filterPriceLevel,
                state.value.selectedPageNumber,
                numberOfRestaurantInPage
            )

            state.value.isSearch -> searchRestaurants(
                state.value.search,
                state.value.selectedPageNumber,
                numberOfRestaurantInPage
            )

            state.value.isFiltered -> filterRestaurants(
                state.value.filterRating,
                state.value.filterPriceLevel,
                state.value.selectedPageNumber,
                numberOfRestaurantInPage
            )

            else -> getRestaurants(
                state.value.selectedPageNumber,
                numberOfRestaurantInPage
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
