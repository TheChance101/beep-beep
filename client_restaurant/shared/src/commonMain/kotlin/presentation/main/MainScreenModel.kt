package presentation.main

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Restaurant
import domain.usecase.IGetRestaurantsUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.restaurantSelection.toUiState

class MainScreenModel(
    private val restaurantId: String,
    private val getOwnerRestaurantsUseCase: IGetRestaurantsUseCase
) : BaseScreenModel<MainScreenUIState, MainScreenUIEffect>(MainScreenUIState()),
    MainScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope


    init {
        updateState { it.copy(selectedRestaurantId = restaurantId) }
        getData()
    }

    private fun getData() {
        tryToExecute(this::callee, this::onSuccess, this::onError)
    }

    private suspend fun callee(): List<Restaurant> {
        return getOwnerRestaurantsUseCase.getOwnerRestaurants()
    }

    private fun onSuccess(restaurants: List<Restaurant>) {
        updateState { it.copy(restaurants = restaurants.toUiState()) }
    }

    private fun onError(errorState: ErrorState) {
        TODO("Not yet implemented")
    }


    override fun onClickBack() {
        sendNewEffect(MainScreenUIEffect.Back)
    }

    override fun onShowMenu() {
        if (state.value.restaurants.isNotEmpty()) {
            updateState { it.copy(expanded = true) }
        }
    }

    override fun onDismissMenu() {
        updateState { it.copy(expanded = false) }
    }

    override fun onRestaurantClicked(restaurantId: String) {
        updateState { it.copy(selectedRestaurantId = restaurantId) }
    }

    override fun onAllMealsCardClicked() {
        sendNewEffect(MainScreenUIEffect.NavigateToAllMeals(restaurantId))
    }

    override fun onRestaurantInfoCardClicked() {
        sendNewEffect(MainScreenUIEffect.NavigateToRestaurantInfo(restaurantId))
    }

    override fun onOrdersCardClicked() {
        sendNewEffect(MainScreenUIEffect.NavigateToOrders(restaurantId))
    }

    override fun onOrdersHistoryCardClicked() {
        sendNewEffect(MainScreenUIEffect.NavigateToOrdersHistory(restaurantId))
    }
}