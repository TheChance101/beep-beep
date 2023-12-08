package presentation.main

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.AddressInfo
import domain.entity.Restaurant
import domain.usecase.ILoginUserUseCase
import domain.usecase.IManageOrderUseCase
import domain.usecase.IManageRestaurantInformationUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.order.LocationUiSate
import presentation.order.toEntity
import presentation.restaurantSelection.toUiState

class MainScreenModel(
    private val manageRestaurant: IManageRestaurantInformationUseCase,
    private val mangeOrders: IManageOrderUseCase,
    private val manageUser: ILoginUserUseCase,
) : BaseScreenModel<MainScreenUIState, MainScreenUIEffect>(MainScreenUIState()),
    MainScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope


    init {
        getData()
        getRestaurantId()
        fetchCharts()
    }

    private fun fetchCharts() {
        updateState { it.copy(isLoading = true) }
        getOrdersCountByDays()
        getOrdersRevenueByDaysBefore()
    }

    private fun saveRestaurantId(restaurantId: String) {
        tryToExecute(
            { manageUser.saveRestaurantId(restaurantId) },
            { onSaveRestaurantIdSuccess() },
            ::onError
        )
    }

    private fun saveRestaurantLocation(addressInfo: AddressInfo) {
        tryToExecute(
            { manageUser.saveRestaurantLocation(addressInfo) },
            { onSaveRestaurantLocationSuccess() },
            ::onError
        )
    }

    private fun onSaveRestaurantIdSuccess() {
        updateState {
            it.copy(
                isLoading = false,
                expanded = false
            )
        }
    }

    private fun onSaveRestaurantLocationSuccess() {
        println("saved location")
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
        updateState { it.copy(isLoading = true) }
        tryToExecute(manageRestaurant::getOwnerRestaurants, this::onSuccess, this::onError)
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
        val hasMultipleRestaurants = restaurants.size > 1
        updateState { it.copy(restaurants = restaurants.toUiState()) }
        if (!hasMultipleRestaurants) {
            println("saving restaurant id in if condition ")
            saveRestaurantId(restaurants.first().id)
            val addressInfo = AddressInfo(restaurants.first().location, restaurants.first().address)
            saveRestaurantLocation(addressInfo)
        }
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

    override fun onRestaurantClicked(
        restaurantId: String,
        location: LocationUiSate,
        address: String
    ) {
        updateState { it.copy(isLoading = true, selectedRestaurantId = restaurantId) }
        saveRestaurantId(restaurantId)
        val addressInfo = AddressInfo(location.toEntity(), address)
        saveRestaurantLocation(addressInfo)
        fetchCharts()
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