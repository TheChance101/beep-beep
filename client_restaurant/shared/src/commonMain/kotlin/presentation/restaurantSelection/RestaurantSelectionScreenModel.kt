package presentation.restaurantSelection

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Restaurant
import domain.usecase.IManageRestaurantInformationUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class RestaurantSelectionScreenModel(
    private val manageRestaurant: IManageRestaurantInformationUseCase,
) : BaseScreenModel<RestaurantScreenUIState, RestaurantSelectionScreenUIEffect>
    (RestaurantScreenUIState()), RestaurantSelectionScreenInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope


    init {
        getData()
    }

    private fun getData() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(this::callee, this::onSuccess, this::onError)
    }

    private fun onSaveRestaurantId(restaurantId: String) {
        tryToExecute(
            { manageRestaurant.saveRestaurantId(restaurantId) },
            { onSaveRestaurantIdSuccess() },
            ::onError
        )
    }

    private fun onSaveRestaurantIdSuccess() {
        sendNewEffect(RestaurantSelectionScreenUIEffect.NavigateToMainScreen)
    }

    private suspend fun callee(): List<Restaurant> {
        return manageRestaurant.getOwnerRestaurants()
    }

    private fun onSuccess(restaurants: List<Restaurant>) {
        updateState { it.copy(restaurants = restaurants.toUiState(), isLoading = false) }
    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(error = errorState.toString(), isLoading = false) }
    }

    override fun onClickRestaurant(restaurantId: String) {
        onSaveRestaurantId(restaurantId)
    }

}
