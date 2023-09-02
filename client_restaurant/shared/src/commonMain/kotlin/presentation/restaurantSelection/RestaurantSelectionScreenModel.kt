package presentation.restaurantSelection

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Restaurant
import domain.usecase.IGetRestaurantsUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class RestaurantSelectionScreenModel(
    private val ownerRestaurants : IGetRestaurantsUseCase
) : BaseScreenModel<RestaurantScreenUIState, RestaurantSelectionScreenUIEffect>
        (RestaurantScreenUIState()), RestaurantSelectionScreenInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope


    init {
        getData()
    }

    private fun getData() {
        tryToExecute(this::callee, this::onSuccess, this::onError)
    }

    private suspend fun callee(): List<Restaurant> {
        return ownerRestaurants.getOwnerRestaurants()
    }

    private fun onSuccess(restaurants: List<Restaurant>) {
        updateState { it.copy(restaurants = restaurants.toUiState(), isLoading = false) }
    }

    private fun onError(errorState: ErrorState) {

    }

    override fun onClickRestaurant(restaurantId: String) {
        sendNewEffect(RestaurantSelectionScreenUIEffect.SelectRestaurant(restaurantId))
    }

}
