package org.thechance.common.presentation.restaurant

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.Time
import org.thechance.common.domain.usecase.IManageLocationUseCase
import org.thechance.common.domain.usecase.IManageRestaurantUseCase
import org.thechance.common.domain.usecase.IValidateRestaurantUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState
import java.util.regex.Pattern


class RestaurantScreenModel(
    private val manageRestaurant: IManageRestaurantUseCase,
    private val handleLocation: IManageLocationUseCase,
    private val iValidateRestaurantUseCase: IValidateRestaurantUseCase
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
        if (state.value.newRestaurantInfoUiState.lat.isEmpty())
            getCurrentLocation()
    }

    private fun getRestaurants(
        pageNumber: Int,
        numberOfRestaurantsInPage: Int,
        restaurantName: String,
        rating: Double?,
        priceLevel: Int?,
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
        updateState { it.copy(isNewRestaurantInfoDialogVisible = true) }
    }

    private fun getCurrentLocation() {
        tryToExecute(
            callee = { handleLocation.getCurrentLocation() },
            onSuccess = ::onGetCurrentLocationSuccess,
            onError = ::onError,
        )
    }

    private fun onGetCurrentLocationSuccess(location: Pair<String, String>) {
        updateState {
            it.copy(
                newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                    lat = location.first,
                    lng = location.second,
                )
            )
        }
    }

    override fun onCancelCreateRestaurantClicked() {
        updateState { it.copy(isNewRestaurantInfoDialogVisible = false, newRestaurantInfoUiState = NewRestaurantInfoUiState()) }
    }

    override fun onRestaurantNameChange(name: String) {
        updateState {
            it.copy(
                newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                    name = name,
                    nameError = ErrorWrapper("Letters only, and Longer than 2.",
                        !iValidateRestaurantUseCase.validateRestaurantName(name)),
                )
            )
        }
    }

    override fun onOwnerUserNameChange(name: String) {
        updateState {
            it.copy(
                newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                    ownerUsername = name,
                    userNameError = ErrorWrapper("Letters only, and Longer than 5.",
                        !iValidateRestaurantUseCase.validateUserName(name)),
                )
            )
        }
    }

    override fun onPhoneNumberChange(number: String) {
        updateState {
            it.copy(
                newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                    phoneNumber = number,
                    phoneNumberError = ErrorWrapper("UnValid number format!",
                        !iValidateRestaurantUseCase.validateNumber(number)),
                )
            )
        }
    }

    override fun onWorkingStartHourChange(hour: String) {
        updateState {
            it.copy(
                newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                    startTime = hour,
                    startTimeError = ErrorWrapper("write in valid format 00:00",
                        !iValidateRestaurantUseCase.validateStartTime(hour)),
                )
            )
        }
    }

    override fun onWorkingEndHourChange(hour: String) {
        updateState {
            it.copy(
                newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                    endTime = hour,
                    endTimeError = ErrorWrapper("write in valid format 00:00",
                        !iValidateRestaurantUseCase.validateEndTime(hour)),
                )
            )
        }
    }

    override fun onLocationChange(location: String) {
        updateState {
            it.copy(
                newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                    location = location,
                    locationError = ErrorWrapper("Location can't be empty!",
                        !iValidateRestaurantUseCase.validateLocation(location)),
                    buttonEnabled = iValidateRestaurantUseCase.validateLocation(location)
                )
            )
        }
    }

    override fun showEditRestaurantMenu(restaurantName: String) {
        updateState {
            it.copy(
                editRestaurantMenu = restaurantName
            )
        }
    }

    override fun hideEditRestaurantMenu() {
        updateState {
            it.copy(
                editRestaurantMenu = ""
            )
        }
    }

    override fun onClickEditRestaurantMenuItem(restaurant: RestaurantUiState.RestaurantDetailsUiState) {
        TODO("Not yet implemented")
    }

    override fun onClickDeleteRestaurantMenuItem(restaurant: RestaurantUiState.RestaurantDetailsUiState) {
        tryToExecute(
            {
                manageRestaurant.deleteRestaurants(
                    Restaurant(
                        id = restaurant.id,
                        name = restaurant.name,
                        ownerUsername = restaurant.ownerUsername,
                        phoneNumber = restaurant.phoneNumber,
                        rating = restaurant.rating,
                        priceLevel = restaurant.priceLevel,
                        workingHours = restaurant.workingHours.replace(" ", "").split("-").run {
                            Pair(
                                Time.parseToCustomTime(get(0)),
                                Time.parseToCustomTime(get(1))
                            )
                        }
                    )
                )
            },
            ::onDeleteRestaurantSuccessfully,
            ::onError
        )
    }

    override fun onCreateNewRestaurantClicked() {
        tryToExecute(
            callee = {
                manageRestaurant.createRestaurant(
                    mutableState.value.newRestaurantInfoUiState.toEntity()
                )
            },
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
                isNewRestaurantInfoDialogVisible = false
            )
        }
    }

    private fun onDeleteRestaurantSuccessfully(restaurant: Restaurant) {
        val restaurants =
            mutableState.value.restaurants.toMutableList().apply { remove(restaurant.toUiState()) }
        updateState {
            it.copy(
                restaurants = restaurants,
                isLoading = false,
            )
        }
        hideEditRestaurantMenu()
    }
}
