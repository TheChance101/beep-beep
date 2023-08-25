package org.thechance.common.presentation.restaurant

import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.usecase.FilterRestaurantsUseCase
import org.thechance.common.domain.usecase.IGetRestaurantsUseCase
import org.thechance.common.domain.usecase.SearchRestaurantsByRestaurantNameUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState
import kotlin.math.ceil


class RestaurantScreenModel(
    private val getRestaurants: IGetRestaurantsUseCase,
    private val searchRestaurants: SearchRestaurantsByRestaurantNameUseCase,
    private val filterRestaurants: FilterRestaurantsUseCase
) : BaseScreenModel<RestaurantUiState, RestaurantUIEffect>(RestaurantUiState()),
    RestaurantInteractionListener {

    init {
        getRestaurants()
    }

    private fun getRestaurants() {
        tryToExecute(getRestaurants::invoke, ::onGetRestaurantSuccessfully, ::onError)
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
            { filterRestaurants.filterRestaurants(rating, priceLevel) },
            ::onGetRestaurantSuccessfully,
            ::onError
        )
    }

    private fun searchFilterRestaurants(restaurantName: String, rating: Double, priceLevel: Int) {
        tryToExecute(
            { filterRestaurants.searchFilterRestaurants(restaurantName, rating, priceLevel) },
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
}
