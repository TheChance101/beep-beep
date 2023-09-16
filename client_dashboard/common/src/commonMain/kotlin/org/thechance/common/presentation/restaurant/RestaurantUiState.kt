package org.thechance.common.presentation.restaurant

import org.thechance.common.presentation.composables.table.Header
import org.thechance.common.presentation.util.ErrorState


data class RestaurantUiState(
    val isLoading: Boolean = true,
    val isNoInternetConnection: Boolean = false,
    val isNewRestaurantInfoDialogVisible: Boolean = false,
    val newRestaurantInfoUiState: NewRestaurantInfoUiState = NewRestaurantInfoUiState(),
    val restaurantAddCuisineDialogUiState: RestaurantAddCuisineDialogUiState = RestaurantAddCuisineDialogUiState(),
    val restaurantFilterDropdownMenuUiState: RestaurantFilterDropdownMenuUiState = RestaurantFilterDropdownMenuUiState(),
    val restaurants: List<RestaurantDetailsUiState> = emptyList(),
    val tableHeader: List<Header> = listOf(
        Header("No.", 1f),
        Header("Name", 3f),
        Header("Owner Username", 3f),
        Header("Phone", 3f),
        Header("Rate", 3f),
        Header("Price Level", 3f),
        Header("Working Hours", 3f),
        Header("", 1f),
    ),
    val numberOfRestaurants: Int = 0,
    val searchQuery: String = "",
    val maxPageCount: Int = 1,
    val selectedPageNumber: Int = 1,
    val numberOfRestaurantsInPage: Int = 10,
    val editRestaurantMenu: String = "",
) {
    data class RestaurantDetailsUiState(
        val id: String,
        val name: String,
        val ownerUsername: String,
        val phone: String,
        val rate: Double,
        val priceLevel: String,
        val openingTime: String,
        val closingTime: String,
    )
}

data class NewRestaurantInfoUiState(
    val name: String = "",
    val nameError: ErrorWrapper = ErrorWrapper(),
    val ownerUsername: String = "",
    val userNameError: ErrorWrapper = ErrorWrapper(),
    val phoneNumber: String = "",
    val phoneNumberError: ErrorWrapper = ErrorWrapper(),
    val openingTime: String = "",
    val startTimeError: ErrorWrapper = ErrorWrapper(),
    val closingTime: String = "",
    val endTimeError: ErrorWrapper = ErrorWrapper(),
    val location: String = "",
    val locationError: ErrorWrapper = ErrorWrapper(),
    val lat: String = "",
    val lng: String = "",
    val buttonEnabled: Boolean = false
)

data class ErrorWrapper(
    val errorMessage: String = "",
    val isError: Boolean = false
)

data class RestaurantFilterDropdownMenuUiState(
    val isFilterDropdownMenuExpanded: Boolean = false,
    val filterRating: Double = 0.0,
    val filterPriceLevel: Int = 1,
    val isFiltered: Boolean = false,
)

data class RestaurantAddCuisineDialogUiState(
    val isVisible: Boolean = false,
    val cuisineName: String = "",
    val cuisines: List<String> = emptyList(),
)