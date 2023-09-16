package org.thechance.common.presentation.restaurant

import kotlinx.coroutines.Job
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.usecase.IManageLocationUseCase
import org.thechance.common.domain.usecase.IManageRestaurantUseCase
import org.thechance.common.domain.usecase.IMangeCuisinesUseCase
import org.thechance.common.domain.usecase.IValidateRestaurantUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState


class RestaurantScreenModel(
    private val manageRestaurant: IManageRestaurantUseCase,
    private val handleLocation: IManageLocationUseCase,
    private val mangeCuisines: IMangeCuisinesUseCase,
    private val iValidateRestaurantUseCase: IValidateRestaurantUseCase
) : BaseScreenModel<RestaurantUiState, RestaurantUIEffect>(RestaurantUiState()),
    RestaurantInteractionListener {

    private var searchJob: Job? = null

    init {
        getRestaurants()
        getCuisines()
        if (state.value.newRestaurantInfoUiState.lat.isEmpty())
            getCurrentLocation()
    }

    private fun getRestaurants() {
        val currentState = state.value
        tryToExecute(
            {
                manageRestaurant.getRestaurant(
                    currentState.selectedPageNumber,
                    currentState.numberOfRestaurantsInPage,
                    currentState.searchQuery,
                    if (currentState.restaurantFilterDropdownMenuUiState.isFiltered)
                        currentState.restaurantFilterDropdownMenuUiState.filterRating else null,
                    if (currentState.restaurantFilterDropdownMenuUiState.isFiltered)
                        currentState.restaurantFilterDropdownMenuUiState.filterPriceLevel.toString() else null,
                )
            },
            ::onGetRestaurantSuccessfully,
            ::onError
        )
    }

    private fun onGetRestaurantSuccessfully(restaurants: DataWrapper<Restaurant>) {
        updateState {
            it.copy(
                restaurants = restaurants.result.toUiState(),
                isLoading = false,
                numberOfRestaurants = restaurants.numberOfResult,
                maxPageCount = restaurants.totalPages
            )
        }
    }

    private fun getCuisines() {
        tryToExecute(
            mangeCuisines::getCuisines,
            ::onGetCuisinesSuccessfully,
            ::onError
        )
    }

    private fun onGetCuisinesSuccessfully(cuisines: List<String>) {
        updateState {
            it.copy(
                restaurantAddCuisineDialogUiState = it.restaurantAddCuisineDialogUiState.copy(
                    cuisines = cuisines,
                )
            )
        }
    }

    private fun onError(error: ErrorState) {
        println(error.toString())
        updateState { it.copy(isLoading = false) }
        when (error) {
            is ErrorState.UserNotExist -> {
                updateState {
                    it.copy(
                        newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                            userNameError = ErrorWrapper(
                                errorMessage = error.errorMessage, isError = true,
                            ),
                        )
                    )
                }
            }

            is ErrorState.RestaurantInvalidName -> {
                updateState {
                    it.copy(
                        newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                            nameError = ErrorWrapper(
                                errorMessage = error.errorMessage, isError = true,
                            )
                        )
                    )
                }
            }

            is ErrorState.RestaurantInvalidPhone -> {
                updateState {
                    it.copy(
                        newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                            phoneNumberError = ErrorWrapper(
                                errorMessage = error.errorMessage, isError = true,
                            )
                        )
                    )
                }
            }

            is ErrorState.RestaurantInvalidLocation -> {
                updateState {
                    it.copy(
                        newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                            locationError = ErrorWrapper(
                                errorMessage = error.errorMessage, isError = true,
                            )
                        )
                    )
                }
            }

            is ErrorState.RestaurantInvalidTime -> {
                updateState {
                    it.copy(
                        newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                            startTimeError = ErrorWrapper(
                                errorMessage = error.errorMessage, isError = true,
                            ),
                            endTimeError = ErrorWrapper(
                                errorMessage = error.errorMessage, isError = true,
                            )
                        )
                    )
                }
            }

            ErrorState.NoConnection -> {
                updateState { it.copy(isNoInternetConnection = true) }
            }

            is ErrorState.CuisineNameAlreadyExisted -> TODO("coming soon...")
            is ErrorState.RestaurantErrorAdd -> TODO("coming soon...")
            is ErrorState.RestaurantInvalidAddress -> TODO("coming soon...")
            is ErrorState.RestaurantInvalidDescription -> TODO("coming soon...")
            is ErrorState.RestaurantInvalidId -> TODO("coming soon...")
            is ErrorState.RestaurantInvalidPage -> TODO("coming soon...")
            is ErrorState.RestaurantInvalidPageLimit -> TODO("coming soon...")
            is ErrorState.RestaurantInvalidRequestParameter -> TODO("coming soon...")
            is ErrorState.RestaurantInvalidUpdateParameter -> TODO("coming soon...")
            is ErrorState.RestaurantNotFound -> TODO("coming soon...")
            else -> {}

        }
    }

    override fun onSaveFilterRestaurantsClicked(rating: Double, priceLevel: String) {
        updateState {
            it.copy(
                restaurantFilterDropdownMenuUiState = it.restaurantFilterDropdownMenuUiState.copy(
                    isFiltered = true
                )
            )
        }
        getRestaurants()
        onDismissDropDownMenu()
    }

    override fun onCancelFilterRestaurantsClicked() {
        onDismissDropDownMenu()
    }


    override fun onSearchChange(restaurantName: String) {
        updateState { it.copy(searchQuery = restaurantName) }
        launchSearchJob()
    }

    private fun launchSearchJob() {
        searchJob?.cancel()
        searchJob = launchDelayed(300L) { getRestaurants() }
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
        getRestaurants()
    }

    override fun onItemPerPageChange(numberOfRestaurantsInPage: Int) {
        updateState { it.copy(numberOfRestaurantsInPage = numberOfRestaurantsInPage) }
        getRestaurants()
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
        clearAddRestaurantInfo()
        clearAddRestaurantErrorInfo()
        updateState { it.copy(isNewRestaurantInfoDialogVisible = false) }
    }

    private fun clearAddRestaurantInfo() {
        updateState {
            it.copy(
                newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                    name = "",
                    ownerUsername = "",
                    phoneNumber = "",
                    openingTime = "",
                    closingTime = "",
                ),
            )
        }
    }

    override fun onRestaurantNameChange(name: String) {
        updateState {
            it.copy(
                newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                    name = name,
                )
            )
        }
    }

    override fun onOwnerUserNameChange(name: String) {
        updateState {
            it.copy(
                newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(ownerUsername = name)
            )
        }
    }

    override fun onPhoneNumberChange(number: String) {
        updateState {
            it.copy(newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(phoneNumber = number))
        }
    }

    override fun onWorkingStartHourChange(hour: String) {
        updateState {
            it.copy(newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(openingTime = hour))
            it.copy(
                newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                    openingTime = hour,
                    startTimeError = ErrorWrapper(
                        "write in valid format 00:00",
                        !iValidateRestaurantUseCase.validateStartTime(hour)
                    ),
                )
            )
        }
    }

    override fun onWorkingEndHourChange(hour: String) {
        updateState {
            it.copy(newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(closingTime = hour))
        }
    }

    override fun onLocationChange(location: String) {
        updateState {
            it.copy(
                newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                    location = location,
                    buttonEnabled = iValidateRestaurantUseCase.validateLocation(location)
                )
            )
        }
    }

    override fun showEditRestaurantMenu(restaurantName: String) {
        updateState { it.copy(editRestaurantMenu = restaurantName) }
    }

    override fun hideEditRestaurantMenu() {
        updateState { it.copy(editRestaurantMenu = "") }
    }

    override fun onClickEditRestaurantMenuItem(restaurant: RestaurantUiState.RestaurantDetailsUiState) {
        TODO("Not yet implemented")
    }

    override fun onClickDeleteRestaurantMenuItem(id: String) {
        tryToExecute(
            { manageRestaurant.deleteRestaurant(id) },
            ::onDeleteRestaurantSuccessfully,
            ::onError
        )
    }

    override fun onFilterClearAllClicked() {
        updateState {
            it.copy(
                restaurantFilterDropdownMenuUiState = it.restaurantFilterDropdownMenuUiState.copy(
                    filterRating = 0.0,
                    filterPriceLevel = 0,
                    isFiltered = false
                )
            )
        }
    }

    override fun onCreateNewRestaurantClicked() {
        updateState { it.copy(isNewRestaurantInfoDialogVisible = true) }
        tryToExecute(
            callee = {
                manageRestaurant.createRestaurant(state.value.newRestaurantInfoUiState.toEntity())
            },
            onSuccess = ::onCreateRestaurantSuccessfully,
            onError = ::onError,
        )
    }

    private fun onCreateRestaurantSuccessfully(restaurant: Restaurant) {
        val newRestaurant =
            mutableState.value.restaurants.toMutableList().apply { add(restaurant.toUiState()) }
        updateState {
            it.copy(restaurants = newRestaurant, isLoading = false, isNewRestaurantInfoDialogVisible = false)
        }
    }

    private fun onDeleteRestaurantSuccessfully(isDeleted: Boolean) {
        updateState { it.copy(isLoading = false) }
        hideEditRestaurantMenu()
        getRestaurants()
    }

    private fun clearAddRestaurantErrorInfo() {
        updateState {
            it.copy(
                newRestaurantInfoUiState = it.newRestaurantInfoUiState.copy(
                    nameError = ErrorWrapper(),
                    userNameError = ErrorWrapper(),
                    phoneNumberError = ErrorWrapper(),
                    startTimeError = ErrorWrapper(),
                    endTimeError = ErrorWrapper(),
                    locationError = ErrorWrapper(),
                )
            )
        }
    }


    // region Cuisine Dialog
    override fun onClickAddCuisine() {
        updateState {
            it.copy(
                restaurantAddCuisineDialogUiState =
                it.restaurantAddCuisineDialogUiState.copy(isVisible = true)
            )
        }
    }

    override fun onCloseAddCuisineDialog() {
        updateState {
            it.copy(
                restaurantAddCuisineDialogUiState =
                it.restaurantAddCuisineDialogUiState.copy(isVisible = false, cuisineName = "")
            )
        }
    }

    override fun onClickCreateCuisine() {
        tryToExecute(
            { mangeCuisines.createCuisine(state.value.restaurantAddCuisineDialogUiState.cuisineName) },
            ::onCreateCuisinesSuccessfully,
            ::onError
        )
    }

    private fun onCreateCuisinesSuccessfully(cuisineName: String) {
        updateState {
            it.copy(
                restaurantAddCuisineDialogUiState = it.restaurantAddCuisineDialogUiState.copy(
                    cuisines = it.restaurantAddCuisineDialogUiState.cuisines.toMutableList().apply {
                        add(cuisineName)
                    },
                    cuisineName = ""
                )
            )
        }
    }

    override fun onClickDeleteCuisine(cuisineName: String) {
        tryToExecute(
            { mangeCuisines.deleteCuisine(cuisineName) },
            ::onDeleteCuisinesSuccessfully,
            ::onError
        )
    }

    private fun onDeleteCuisinesSuccessfully(cuisineName: String) {
        updateState {
            it.copy(
                restaurantAddCuisineDialogUiState = it.restaurantAddCuisineDialogUiState.copy(
                    cuisines = it.restaurantAddCuisineDialogUiState.cuisines.toMutableList().apply {
                        remove(cuisineName)
                    }
                )
            )
        }
    }

    override fun onChangeCuisineName(cuisineName: String) {
        updateState {
            it.copy(
                restaurantAddCuisineDialogUiState = it.restaurantAddCuisineDialogUiState.copy(
                    cuisineName = cuisineName
                )
            )
        }
    }

    // endregion
}
