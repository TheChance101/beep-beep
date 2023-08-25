package presentation.restaurantSelection

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Restaurant
import domain.usecase.IGetOwnerRestaurantsInformationUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class RestaurantSelectionScreenModel(private val ownerId: String) :
    BaseScreenModel<RestaurantScreenUIState, RestaurantSelectionScreenUIEffect>
        (RestaurantScreenUIState()),
    RestaurantSelectionScreenInteractionListener, KoinComponent {

    override val viewModelScope: CoroutineScope = coroutineScope
    private val getOwnerRestaurantsInformationUseCase: IGetOwnerRestaurantsInformationUseCase by inject()

    init {
        getData()
    }

    private fun getData() {
        tryToExecute(this::callee, this::onSuccess, this::onError)
    }

    private suspend fun callee(): List<Restaurant> {
        return getOwnerRestaurantsInformationUseCase(ownerId)
    }

    private fun onSuccess(restaurants: List<Restaurant>) {
        updateState { it.copy(restaurants = restaurants.toUiState(), isLoading = false) }
    }

    private fun onError(errorState: ErrorState) {

    }

    override fun onRestaurantItemClick(restaurantId: String) {
        sendNewEffect(RestaurantSelectionScreenUIEffect.SelectRestaurant(restaurantId))
    }

}
