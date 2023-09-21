package org.thechance.common.presentation.restaurant

import org.thechance.common.domain.entity.Cuisine
import org.thechance.common.domain.entity.RestaurantInformation
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.presentation.composables.table.Header


data class RestaurantUiState(
    val isLoading: Boolean = true,
    val isNoInternetConnection: Boolean = false,
    val isNewRestaurantInfoDialogVisible: Boolean = false,
    val restaurantInformationUIState: RestaurantInformationUIState = RestaurantInformationUIState(),
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
        val priceLevel: Int,
        val openingTime: String,
        val closingTime: String,
        val isExpanded: Boolean = false,
    )
}

fun Restaurant.toUiState(): RestaurantUiState.RestaurantDetailsUiState =
    RestaurantUiState.RestaurantDetailsUiState(
        id = id,
        name = name,
        ownerUsername = ownerUsername,
        phone = phone,
        rate = rate,
        priceLevel = priceLevel.count { it == '$' } ,
        openingTime = openingTime,
        closingTime = closingTime,
    )

fun List<Restaurant>.toRestaurantsUIState() = map(Restaurant::toUiState)

data class RestaurantInformationUIState(
    val name: String = "",
    val nameError: ErrorWrapper? = null,
    val ownerUsername: String = "",
    val userNameError: ErrorWrapper? = null,
    val phoneNumber: String = "",
    val phoneNumberError: ErrorWrapper? = null,
    val openingTime: String = "",
    val startTimeError: ErrorWrapper? = null,
    val closingTime: String = "",
    val endTimeError: ErrorWrapper? = null,
    val location: String = "",
    val locationError: ErrorWrapper? = null,
    val latitude: String = "",
    val longitude: String = "",
    val buttonEnabled: Boolean = true
)

fun Restaurant.toUIState() = RestaurantInformationUIState(
    name = name,
    ownerUsername = ownerUsername,
    phoneNumber = phone,
    openingTime = openingTime,
    closingTime = closingTime,
    location = location.latitude.toString() + "," + location.longitude.toString()
)

fun RestaurantInformationUIState.toEntity() = RestaurantInformation(
    name = name,
    ownerUsername = ownerUsername,
    phoneNumber = phoneNumber,
    location = location,
    openingTime = openingTime,
    closingTime = closingTime,
)

data class ErrorWrapper(
    val errorMessage: String = "",
    val isError: Boolean = false
)

data class RestaurantFilterDropdownMenuUiState(
    val isFilterDropdownMenuExpanded: Boolean = false,
    val filterRating: Double = 0.0,
    val filterPriceLevel: Int = 0,
    val isFiltered: Boolean = false,
)

data class RestaurantAddCuisineDialogUiState(
    val isVisible: Boolean = false,
    val cuisineName: String = "",
    val cuisines: List<CuisineUiState> = emptyList(),
    val cuisineNameError: ErrorWrapper = ErrorWrapper(),
)

data class CuisineUiState(
    val id: String,
    val name: String,
)

fun Cuisine.toUiState(): CuisineUiState {
    return CuisineUiState(
        id = id,
        name = name,
    )
}

fun List<Cuisine>.toUiState(): List<CuisineUiState> {
    return map(Cuisine::toUiState)
}