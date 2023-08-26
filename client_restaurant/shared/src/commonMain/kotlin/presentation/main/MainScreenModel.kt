package presentation.main

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Restaurant
import domain.usecase.IGetOwnerRestaurantsUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.restaurantSelection.toUiState

class MainScreenModel(private val restaurantId: String) : BaseScreenModel<MainScreenUIState, MainScreenUIEffect>(MainScreenUIState()),
    MainScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    private val getOwnerRestaurantsInformationUseCase: IGetOwnerRestaurantsUseCase by inject()

    init {
        updateState { it.copy(selectedRestaurantId = restaurantId) }
        getData()
    }

    private fun getData() {
        tryToExecute(this::callee, this::onSuccess, this::onError)
    }

    private suspend fun callee(): List<Restaurant> {
        return getOwnerRestaurantsInformationUseCase.getOwnerRestaurants("")
    }

    private fun onSuccess(restaurants: List<Restaurant>) {
        updateState {
            it.copy(
                restaurantName = "restaurantName 1",
                restaurants = restaurants.toUiState(),
                //TODO NEED to move to usecase and fake data...
                charts = listOf(
                    ChartItemUiState(
                        title = "Revenue",
                        points = listOf(
                            "Mon" to 100,
                            "Tue" to 200,
                            "Wed" to 300,
                            "Thu" to 400,
                            "Fri" to 500,
                            "Sat" to 600,
                            "Sun" to 700
                        ),
                        totalThisWeek = 4700,
                        sign = '$',
                        isRevenue = true
                    ),
                    ChartItemUiState(
                        title = "Orders",
                        points = listOf(
                            "Mon" to 10,
                            "Tue" to 20,
                            "Wed" to 30,
                            "Thu" to 40,
                            "Fri" to 50,
                            "Sat" to 60,
                            "Sun" to 70
                        ),
                        totalThisWeek = 280,
                        sign = null,
                        isRevenue = false
                    )
                )
            )
        }
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

    override fun onRestaurantClick(restaurantId: String) {
        updateState { it.copy(selectedRestaurantId = restaurantId) }
    }

    override fun onCardClick(cardIndex: Int) {
        val restaurantId = state.value.selectedRestaurantId
        when(cardIndex) {
            0 -> sendNewEffect(MainScreenUIEffect.NavigateToAllMeals(restaurantId))
            1 -> sendNewEffect(MainScreenUIEffect.NavigateToOrders(restaurantId))
            2 -> sendNewEffect(MainScreenUIEffect.NavigateToRestaurantInfo(restaurantId))
            3 -> sendNewEffect(MainScreenUIEffect.NavigateToOrdersHistory(restaurantId))
        }
    }
}