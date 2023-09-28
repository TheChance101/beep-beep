package org.thechance.common.presentation.restaurant

import kotlinx.coroutines.Job
import org.thechance.common.domain.entity.Cuisine
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.LocationInfo
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.usecase.IManageLocationUseCase
import org.thechance.common.domain.usecase.IManageRestaurantUseCase
import org.thechance.common.domain.usecase.IMangeCuisinesUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState


class RestaurantScreenModel(
    private val manageRestaurant: IManageRestaurantUseCase,
    private val manageLocation: IManageLocationUseCase,
    private val mangeCuisines: IMangeCuisinesUseCase,
) : BaseScreenModel<RestaurantUiState, RestaurantUIEffect>(RestaurantUiState()),
    RestaurantInteractionListener {

    private var searchJob: Job? = null
    private var limitJob: Job? = null

    init {
        initRestaurantScreen()
    }

    private fun initRestaurantScreen() {
        getRestaurants()
        getCuisines()
        if (state.value.restaurantInformationUIState.latitude.isEmpty())
            getCurrentLocation()
    }

    private fun getRestaurants() {

        tryToExecute(
                {
                    mutableState.value.run {
                        manageRestaurant.getRestaurant(
                                selectedPageNumber,
                                numberOfRestaurantsInPage,
                                searchQuery,
                                restaurantFilterDropdownMenuUiState.filterRating,
                                restaurantFilterDropdownMenuUiState.filterPriceLevel,
                        )
                    }
                },
                ::onGetRestaurantSuccessfully,
                ::onError
        )
    }

    private fun onGetRestaurantSuccessfully(restaurants: DataWrapper<Restaurant>) {
        updateState {
            it.copy(
                    hasConnection = true,
                    restaurants = restaurants.result.toRestaurantsUIState(),
                    isLoading = false,
                    numberOfRestaurants = restaurants.numberOfResult,
                    maxPageCount = restaurants.totalPages
            )
        }
        if (state.value.selectedPageNumber > state.value.maxPageCount) {
            onPageClicked(state.value.maxPageCount)
        }
    }

    private fun getCuisines() {
        tryToExecute(
                mangeCuisines::getCuisines,
                ::onGetCuisinesSuccessfully,
                ::onError
        )
    }

    private fun onGetCuisinesSuccessfully(cuisines: List<Cuisine>) {
        updateState {
            it.copy(
                    restaurantAddCuisineDialogUiState = it.restaurantAddCuisineDialogUiState.copy(
                            cuisines = cuisines.toUiState(),
                    )
            )
        }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(isLoading = false) }
        when (error) {
            is ErrorState.MultipleErrors -> {
                val errorStates = error.errors
                updateState {
                    it.copy(
                        restaurantInformationUIState = it.restaurantInformationUIState.copy(
                            nameError = errorStates.firstInstanceOfOrNull<ErrorState.RestaurantInvalidName>()
                                ?.let { error ->
                                    ErrorWrapper(error.errorMessage, true)
                                },
                            userNameError = errorStates.firstInstanceOfOrNull<ErrorState.InvalidUserName>()
                                ?.let { error ->
                                    ErrorWrapper(error.errorMessage, true)
                                },
                            phoneNumberError = errorStates.firstInstanceOfOrNull<ErrorState.RestaurantInvalidPhone>()
                                ?.let { error ->
                                    ErrorWrapper(error.errorMessage, true)
                                },
                            startTimeError = errorStates.firstInstanceOfOrNull<ErrorState.RestaurantInvalidTime>()
                                ?.let { error ->
                                    ErrorWrapper(error.errorMessage, true)
                                },
                            endTimeError = errorStates.firstInstanceOfOrNull<ErrorState.RestaurantInvalidTime>()
                                ?.let { error ->
                                    ErrorWrapper(error.errorMessage, true)
                                },
                            locationError = errorStates.firstInstanceOfOrNull<ErrorState.RestaurantInvalidLocation>()
                                ?.let { error ->
                                    ErrorWrapper(error.errorMessage, true)
                                },
                        )
                    )
                }
                updateState { it.copy(
                    restaurantAddCuisineDialogUiState = it.restaurantAddCuisineDialogUiState.copy(
                            cuisineNameError = ErrorWrapper(errorStates
                                .firstInstanceOfOrNull<ErrorState.CuisineNameAlreadyExisted>()
                                ?.errorMessage ?: "", true)
                    ))
                }


            }
            is ErrorState.NoConnection -> {
                updateState {
                    it.copy(hasConnection = false)
                }
            }

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
        launchLimitJob()
    }

    private fun launchLimitJob() {
        limitJob?.cancel()
        limitJob = launchDelayed(300L) { getRestaurants() }
    }

    override fun onAddNewRestaurantClicked() {
        clearRestaurantInfoErrorState()
        clearAddRestaurantInfo()
        updateState { it.copy(isNewRestaurantInfoDialogVisible = true, isEditMode = false) }
    }

    private fun onUpdateRestaurantSuccessfully(restaurant: Restaurant) {
        updateState { it.copy(isLoading = false, isNewRestaurantInfoDialogVisible = false) }
        clearAddRestaurantInfo()
        getRestaurants()
    }

    override fun onUpdateRestaurantClicked(restaurantId: String) {
        updateState { it.copy(isLoading = true) }
        clearRestaurantInfoErrorState()
        tryToExecute(
            {
                manageRestaurant.updateRestaurant(
                    restaurantId,
                    state.value.restaurantInformationUIState.ownerId,
                    state.value.restaurantInformationUIState.toEntity()
                )
            },
            ::onUpdateRestaurantSuccessfully,
            ::onError,
        )
    }

    override fun onRetry() {
        initRestaurantScreen()
    }

    private fun getCurrentLocation() {
        tryToExecute(
                callee = { manageLocation.getCurrentLocation() },
                onSuccess = ::onGetCurrentLocationSuccess,
                onError = ::onError,
        )
    }

    private fun onGetCurrentLocationSuccess(location: LocationInfo) {
        updateState {
            it.copy(
                    restaurantInformationUIState = it.restaurantInformationUIState.copy(
                            latitude = location.latitude.toString(),
                            longitude = location.longitude.toString(),
                    )
            )
        }
    }

    override fun onCancelCreateRestaurantClicked() {
        clearAddRestaurantInfo()
        clearRestaurantInfoErrorState()
        updateState { it.copy(isNewRestaurantInfoDialogVisible = false) }
    }

    private fun clearAddRestaurantInfo() {
        updateState {
            it.copy(
                    restaurantInformationUIState = it.restaurantInformationUIState.copy(
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
                    restaurantInformationUIState = it.restaurantInformationUIState.copy(
                            name = name,
                    )
            )
        }
    }

    override fun onOwnerUserNameChange(name: String) {
        updateState {
            it.copy(
                    restaurantInformationUIState = it.restaurantInformationUIState.copy(ownerUsername = name)
            )
        }
    }

    override fun onPhoneNumberChange(number: String) {
        updateState {
            it.copy(restaurantInformationUIState = it.restaurantInformationUIState.copy(phoneNumber = number))
        }
    }

    override fun onWorkingStartHourChange(hour: String) {
        updateState {
            it.copy(restaurantInformationUIState = it.restaurantInformationUIState.copy(openingTime = hour))
            it.copy(
                    restaurantInformationUIState = it.restaurantInformationUIState.copy(
                            openingTime = hour,
//                    startTimeError = ErrorWrapper(
//                        "write in valid format 00:00",
////                        !iValidateRestaurantUseCase.validateStartTime(hour)
//                    ),
                    )
            )
        }
    }

    override fun onWorkingEndHourChange(hour: String) {
        updateState {
            it.copy(restaurantInformationUIState = it.restaurantInformationUIState.copy(closingTime = hour))
        }
    }

    override fun onLocationChange(location: String) {
        updateState {
            it.copy(
                    restaurantInformationUIState = it.restaurantInformationUIState.copy(
                            location = location,
                    )
            )
        }
    }

    override fun onShowRestaurantMenu(restaurantId: String) {
        setRestaurantMenuVisibility(restaurantId, true)
    }

    override fun onHideRestaurantMenu(restaurantId: String) {
        setRestaurantMenuVisibility(restaurantId, false)
    }

    private fun setRestaurantMenuVisibility(id: String, isExpanded: Boolean) {
        val currentRestaurantState = state.value.restaurants
        val selectedRestaurantState = currentRestaurantState.first { it.id == id }
        val updateRestaurantState = selectedRestaurantState.copy(isExpanded = isExpanded)
        updateState {
            it.copy(
                restaurants = currentRestaurantState.toMutableList()
                    .apply { set(indexOf(selectedRestaurantState), updateRestaurantState) }
            )
        }
    }

    private fun onGetRestaurantByIdSuccessfully(restaurant: Restaurant) {
        val restaurantUiState = restaurant.toUIState()
        updateState {
            it.copy(restaurantInformationUIState = restaurantUiState)
        }
        updateState {
            it.copy(
                restaurantInformationUIState =
                mutableState.value.restaurantInformationUIState.copy(ownerId = restaurant.ownerId)
            )
        }
    }

    private fun getRestaurantById(id: String) {
        tryToExecute(
            { manageRestaurant.getRestaurantById(id) },
            ::onGetRestaurantByIdSuccessfully,
            ::onError
        )
    }

    override fun onClickEditRestaurantMenuItem(restaurantId: String) {
        getRestaurantById(restaurantId)
        setRestaurantMenuVisibility(restaurantId, false)
        updateState { it.copy(isNewRestaurantInfoDialogVisible = true, isEditMode = true) }
    }

    override fun onClickDeleteRestaurantMenuItem(id: String) {
        tryToExecute(
            { manageRestaurant.deleteRestaurant(id) },
            { onDeleteRestaurantSuccessfully(id) },
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
        clearRestaurantInfoErrorState()
        tryToExecute(
            { manageRestaurant.createRestaurant(state.value.restaurantInformationUIState.toEntity()) },
            ::onCreateRestaurantSuccessfully,
            ::onError,
        )
    }

    private fun onCreateRestaurantSuccessfully(restaurant: Restaurant) {
        clearAddRestaurantInfo()
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

    private fun onDeleteRestaurantSuccessfully(id: String) {
        updateState { it.copy(isLoading = false) }
        setRestaurantMenuVisibility(id, false)
        getRestaurants()
    }

    private fun clearRestaurantInfoErrorState() {
        updateState {
            it.copy(
                    restaurantInformationUIState = it.restaurantInformationUIState.copy(
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

    private fun clearCuisineErrorState() {
        updateState {
            it.copy(
                    restaurantAddCuisineDialogUiState =
                    it.restaurantAddCuisineDialogUiState.copy(cuisineNameError = ErrorWrapper())
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
        clearCuisineErrorState()
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

    private fun onCreateCuisinesSuccessfully(cuisine: Cuisine) {
        clearCuisineErrorState()
        updateState {
            it.copy(
                    restaurantAddCuisineDialogUiState = it.restaurantAddCuisineDialogUiState.copy(
                            cuisines = it.restaurantAddCuisineDialogUiState.cuisines.toMutableList()
                                .apply {
                                    add(cuisine.toUiState())
                                },
                            cuisineName = ""
                    )
            )
        }
    }

    override fun onClickDeleteCuisine(cuisineId: String) {
        tryToExecute(
            { mangeCuisines.deleteCuisine(cuisineId) },
            { onDeleteCuisinesSuccessfully(cuisineId) },
            ::onError
        )
    }

    private fun onDeleteCuisinesSuccessfully(cuisineId: String) {
        updateState {
            it.copy(
                    restaurantAddCuisineDialogUiState = it.restaurantAddCuisineDialogUiState.copy(
                            cuisines = it.restaurantAddCuisineDialogUiState.cuisines.toMutableList()
                                .apply {
                                    val cuisine =
                                        this.find { cuisineUiState -> cuisineUiState.id == cuisineId }
                                    remove(cuisine)
                                }
                    )
            )
        }
    }

    override fun onChangeCuisineName(cuisineName: String) {
        clearCuisineErrorState()
        updateState {
            it.copy(
                restaurantAddCuisineDialogUiState =
                it.restaurantAddCuisineDialogUiState.copy(cuisineName = cuisineName)
            )
        }
    }

    // endregion
}