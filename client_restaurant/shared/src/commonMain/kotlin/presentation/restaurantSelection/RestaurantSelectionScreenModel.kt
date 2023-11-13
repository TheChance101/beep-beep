package presentation.restaurantSelection

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Restaurant
import domain.usecase.IGetRestaurantsUseCase
import domain.usecase.ILoginUserUseCase
import domain.usecase.IManageRestaurantInformationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class RestaurantSelectionScreenModel(
    private val ownerRestaurants: IGetRestaurantsUseCase,
    private val manageRestaurant: IManageRestaurantInformationUseCase,
    private val user:ILoginUserUseCase
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
        return ownerRestaurants.getOwnerRestaurants()
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
