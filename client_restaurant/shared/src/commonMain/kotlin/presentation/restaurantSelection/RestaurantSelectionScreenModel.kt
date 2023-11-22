package presentation.restaurantSelection

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.AddressInfo
import domain.entity.Location
import domain.entity.Restaurant
import domain.usecase.ILoginUserUseCase
import domain.usecase.IManageRestaurantInformationUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.order.LocationUiSate
import presentation.order.toEntity

class RestaurantSelectionScreenModel(
    private val manageRestaurant: IManageRestaurantInformationUseCase,
    private val manageUser: ILoginUserUseCase,
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
    private fun saveRestaurantLocation(addressInfo: AddressInfo) {
        tryToExecute(
            { manageUser.saveRestaurantLocation(addressInfo) },
            { onSaveRestaurantLocationSuccess() },
            ::onError
        )
    }

    private fun onSaveRestaurantLocationSuccess() {
        println("main saving location success")
    }

    override fun onClickRestaurant(restaurantId: String, location: LocationUiSate, address: String) {
        onSaveRestaurantId(restaurantId)
        val addressInfo = AddressInfo(location.toEntity(),address)
        saveRestaurantLocation(addressInfo)
        updateState { it.copy(isLoading = true) }    }


}