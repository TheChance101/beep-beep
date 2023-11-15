package presentation.restaurants

import androidx.paging.PagingData
import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Restaurant
import domain.usecase.IExploreRestaurantUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import presentation.base.BaseScreenModel
import presentation.base.ErrorState


class RestaurantsScreenModel(
    private val offerId: String? = null,
    private val manageRestaurant: IExploreRestaurantUseCase,
) : BaseScreenModel<RestaurantsUIState, RestaurantsUIEffect>(RestaurantsUIState()),
    RestaurantsListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getData()
    }

    private fun getData() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { manageRestaurant.getRestaurants() },
            ::onSuccess,
            ::onError
        )
    }

    private fun onSuccess(restaurants: Flow<PagingData<Restaurant>>) {
        updateState { it.copy(restaurants = restaurants.toUIState(), isLoading = false) }
    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(isLoading = false) }
        when (errorState) {
            is ErrorState.NoInternet -> {
                updateState { it.copy(error = errorState) }
            }

            else -> {
                updateState { it.copy(error = errorState) }
            }
        }
    }


    override fun onRestaurantClicked(id: String) {
        sendNewEffect(RestaurantsUIEffect.NavigateToRestaurantDetails(id))
    }

    override fun onBackClicked() {
        sendNewEffect(RestaurantsUIEffect.NavigateBack)
    }


}
