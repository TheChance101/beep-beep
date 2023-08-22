package org.thechance.common.presentation.restaurant

import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.usecase.IGetRestaurantsUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState
import kotlin.math.ceil


class RestaurantScreenModel(
    private val getRestaurants: IGetRestaurantsUseCase
) : BaseScreenModel<RestaurantUiState, RestaurantUIEffect>(RestaurantUiState()),
    RestaurantInteractionListener {

    init {
        getRestaurant()
    }

    private fun getRestaurant() {
        tryToExecute(getRestaurants::getRestaurants, ::onGetRestaurantSuccessfully, ::onError)
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

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error, isLoading = false) }
    }


    override fun onSearchChange(text: String) {
        updateState { it.copy(search = text) }
    }

    override fun onClickDropDownMenu() {
        updateState { it.copy(isFilterDropdownMenuExpanded = true) }
    }

    override fun onDismissDropDownMenu() {
        updateState { it.copy(isFilterDropdownMenuExpanded = false) }
    }

    override fun onClickFilterRating(rating: Double) {
        updateState { it.copy(filterRating = rating) }
    }

    override fun onClickFilterPrice(priceLevel: Int) {
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
