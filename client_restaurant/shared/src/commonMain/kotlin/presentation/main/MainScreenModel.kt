package presentation.main

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Restaurant
import domain.usecase.IGetRestaurantsUseCase
import domain.usecase.ILoginUserUseCase
import domain.usecase.IManageOrderUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.restaurantSelection.toUiState

class MainScreenModel(
    private val getOwnerRestaurants: IGetRestaurantsUseCase,
    private val mangeOrders: IManageOrderUseCase,
    private val manageUser: ILoginUserUseCase,
) : BaseScreenModel<MainScreenUIState, MainScreenUIEffect>(MainScreenUIState()),
    MainScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope


    init {
        getRestaurantId()
        getData()
        getOrdersCountByDays()
        getOrdersRevenueByDaysBefore()
    }


    private fun saveRestaurantId(restaurantId: String) {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { manageUser.saveRestaurantId(restaurantId) },
            { onSaveRestaurantIdSuccess(restaurantId) },
            ::onError
        )
    }

    private fun onSaveRestaurantIdSuccess(restaurantId: String) {
        updateState {
            it.copy(
                isLoading = false,
                selectedRestaurantId = restaurantId,
                expanded = false
            )
        }
    }

    private fun getRestaurantId() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { manageUser.getRestaurantId() },
            ::onGetRestaurantIdSuccess,
            ::onError
        )
    }

    private fun onGetRestaurantIdSuccess(restaurantId: String) {
        updateState { it.copy(selectedRestaurantId = restaurantId, isLoading = false) }
    }

    private fun getData() {
        tryToExecute(this::callee, this::onSuccess, this::onError)
    }

    private suspend fun callee(): List<Restaurant> {
        return getOwnerRestaurants.getOwnerRestaurants()
    }

    private fun getOrdersCountByDays() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { mangeOrders.getOrdersCountByDaysBefore(state.value.selectedRestaurantId, 7) },
            ::onGetOrdersCountByDaysSuccessfully,
            ::onError
        )
    }

    private fun onGetOrdersCountByDaysSuccessfully(data: List<Pair<String, Double>>) {
        updateState {
            it.copy(
                ordersCountStatistics = data.toChartsItemUiState(),
                isLoading = false
            )
        }
    }

    private fun getOrdersRevenueByDaysBefore() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { mangeOrders.getOrdersRevenueByDaysBefore(state.value.selectedRestaurantId, 7) },
            ::onGetOrdersRevenueByDaysBeforeSuccessfully,
            ::onError
        )
    }

    private fun onGetOrdersRevenueByDaysBeforeSuccessfully(data: List<Pair<String, Double>>) {
        updateState {
            it.copy(
                revenueStatistics = data.toChartsItemUiState(),
                isLoading = false
            )
        }
    }


    private fun onSuccess(restaurants: List<Restaurant>) {
        updateState { it.copy(restaurants = restaurants.toUiState()) }

    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(error = errorState, isLoading = false) }
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
        saveRestaurantId(restaurantId)
        getOrdersCountByDays()
        getOrdersRevenueByDaysBefore()
    }

    override fun onAllMealsCardClicked() {
        sendNewEffect(MainScreenUIEffect.NavigateToAllMeals(state.value.selectedRestaurantId))
    }

    override fun onRestaurantInfoCardClicked() {
        sendNewEffect(MainScreenUIEffect.NavigateToRestaurantInfo(state.value.selectedRestaurantId))
    }

    override fun onOrdersCardClicked() {
        sendNewEffect(MainScreenUIEffect.NavigateToOrders(state.value.selectedRestaurantId))
    }

    override fun onOrdersHistoryCardClicked() {
        sendNewEffect(MainScreenUIEffect.NavigateToOrdersHistory(state.value.selectedRestaurantId))
    }
}