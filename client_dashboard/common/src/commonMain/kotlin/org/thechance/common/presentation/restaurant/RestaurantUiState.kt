package org.thechance.common.presentation.restaurant

import androidx.compose.runtime.Composable
import org.thechance.common.domain.entity.Cuisine
import org.thechance.common.presentation.composables.table.Header
import org.thechance.common.presentation.resources.Resources


data class RestaurantUiState(
    val isLoading: Boolean = true,
    val isNoInternetConnection: Boolean = false,
    val isNewRestaurantInfoDialogVisible: Boolean = false,
    val newRestaurantInfoUiState: NewRestaurantInfoUiState = NewRestaurantInfoUiState(),
    val restaurantAddCuisineDialogUiState: RestaurantAddCuisineDialogUiState = RestaurantAddCuisineDialogUiState(),
    val restaurantFilterDropdownMenuUiState: RestaurantFilterDropdownMenuUiState = RestaurantFilterDropdownMenuUiState(),
    val restaurants: List<RestaurantDetailsUiState> = emptyList(),
    val numberOfRestaurants: Int = 0,
    val searchQuery: String = "",
    val maxPageCount: Int = 1,
    val selectedPageNumber: Int = 1,
    val numberOfRestaurantsInPage: Int = 10,
    val editRestaurantMenu: String = "",
) {
    val tableHeader: List<Header>
       @Composable get() = listOf(
            Header(Resources.Strings.number, 1f),
            Header(Resources.Strings.name, 3f),
            Header(Resources.Strings.ownerUsername, 3f),
            Header(Resources.Strings.phone, 3f),
            Header(Resources.Strings.rate, 3f),
            Header(Resources.Strings.priceLevel, 3f),
            Header(Resources.Strings.workingHours, 3f),
            Header("", 1f),
    )
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