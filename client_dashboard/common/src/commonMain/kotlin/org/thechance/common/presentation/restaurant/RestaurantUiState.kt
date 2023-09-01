package org.thechance.common.presentation.restaurant

import org.thechance.common.presentation.composables.table.Header
import org.thechance.common.presentation.util.ErrorState


data class RestaurantUiState(
    val isLoading: Boolean = false,
    val error: ErrorState = ErrorState.UnKnownError,
    val isAddNewRestaurantDialogVisible: Boolean = false,
    val addNewRestaurantDialogUiState: AddRestaurantDialogUiState = AddRestaurantDialogUiState(),
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
    val search: String = "",
    val maxPageCount: Int = 1,
    val selectedPageNumber: Int = 1,
    val numberOfRestaurantsInPage: Int = 10,
) {
    data class RestaurantDetailsUiState(
        val name: String,
        val ownerUsername: String,
        val phoneNumber: String,
        val rating: Double,
        val priceLevel: Int,
        val workingHours: String,
    )
}

data class AddRestaurantDialogUiState(
    val name: String = "",
    val ownerUsername: String = "",
    val phoneNumber: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val location: String = "",
    val currentLocation: String = "",
)

data class RestaurantFilterDropdownMenuUiState(
    val isFilterDropdownMenuExpanded: Boolean = false,
    val filterRating: Double = 0.0,
    val filterPriceLevel: Int = 1,
    val isFiltered: Boolean = false,
)
