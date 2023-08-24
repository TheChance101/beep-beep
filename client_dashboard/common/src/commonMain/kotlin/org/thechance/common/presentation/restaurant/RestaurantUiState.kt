package org.thechance.common.presentation.restaurant

import org.thechance.common.presentation.composables.table.Header
import org.thechance.common.presentation.util.ErrorState


data class RestaurantUiState(
    val isLoading: Boolean = false,
    val error: ErrorState = ErrorState.UnKnownError,
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
    val isFilterDropdownMenuExpanded: Boolean = false,
    val filterRating: Double = 0.0,
    val filterPriceLevel: Int = 0,
    val maxPageCount: Int = 1,
    val selectedPageNumber: Int = 1,
    val numberOfItemsInPage: Int = 10,
    val isFiltered: Boolean = false,
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
