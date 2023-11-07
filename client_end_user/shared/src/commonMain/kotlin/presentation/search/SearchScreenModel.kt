package presentation.search

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Meal
import domain.entity.Restaurant
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.IManageCartUseCase
import domain.usecase.ISearchUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.resturantDetails.MealUIState
import presentation.resturantDetails.toUIState

class SearchScreenModel(
    private val search: ISearchUseCase,
    private val manageAuthentication: IManageAuthenticationUseCase,
    private val manageCart: IManageCartUseCase,
) : BaseScreenModel<SearchUiState, SearchUiEffect>(SearchUiState()), SearchInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope
    private var searchJob: Job? = null

    init {
        getMealAndRestaurant()
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        tryToExecute(
            { manageAuthentication.getAccessToken() },
            ::onCheckLoginSuccess,
            ::onError
        )
    }

    private fun onCheckLoginSuccess(accessToken: Flow<String>) {
        coroutineScope.launch {
            accessToken.distinctUntilChanged().collectLatest { token ->
                if (token.isNotEmpty()) {
                    updateState { it.copy(isLogin = true) }
                } else {
                    updateState { it.copy(isLogin = false) }
                }
            }
        }
    }

    override fun onSearchInputChanged(keyword: String) {
        updateState { it.copy(query = keyword) }
        launchSearchJob()
    }

    override fun onRestaurantClicked(restaurantId: String) {
        sendNewEffect(SearchUiEffect.NavigateToRestaurant(restaurantId))
    }

    override fun onMealClicked(meal: MealUIState) {
        if (!state.value.showMealSheet) {
            updateState { it.copy(showMealSheet = true, selectedMeal = meal) }
        }
    }

    override fun onDismissSheet() {
        updateState { it.copy(showLoginSheet = false, showMealSheet = false) }
    }

    private fun launchSearchJob() {
        searchJob?.cancel()
        searchJob = launchDelayed(300L) { this@SearchScreenModel.getMealAndRestaurant() }
    }

    private fun getMealAndRestaurant() {
        tryToExecute(
            { search.search(query = state.value.query) },
            ::onSuccess,
            ::onError
        )
    }

    private fun onSuccess(result: Pair<List<Restaurant>, List<Meal>>) {
        updateState {
            it.copy(
                restaurants = result.first.toExploreUiState(),
                meals = result.second.toUIState()
            )
        }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error) }
    }

    override fun onIncreaseMealQuantity() {
        val quality = state.value.selectedMeal.quantity + 1
        updateState {
            it.copy(
                selectedMeal = state.value.selectedMeal.copy(
                    quantity = quality,
                    totalPrice = state.value.selectedMeal.price * quality
                )
            )
        }
    }

    override fun onDecreaseMealQuantity() {
        if (state.value.selectedMeal.quantity == 1) return
        updateState {
            val quality = state.value.selectedMeal.quantity - 1
            it.copy(
                selectedMeal = state.value.selectedMeal.copy(
                    quantity = quality, totalPrice = state.value.selectedMeal.price * quality
                )
            )
        }
    }

    override fun onAddToCart() {
        if (state.value.isLogin) {
            updateState { it.copy(isAddToCartLoading = true) }
            tryToExecute(
                {
                    manageCart.addMealTCart(
                        restaurantId = state.value.selectedMeal.restaurantId,
                        quantity = state.value.selectedMeal.quantity,
                        mealId = state.value.selectedMeal.id
                    )
                },
                ::onAddToCartSuccess,
                ::onAddToCartError
            )
        } else {
            updateState { it.copy(showMealSheet = false, showLoginSheet = true) }
        }
    }

    private fun onAddToCartSuccess(success: Boolean) {
        updateState { it.copy(isAddToCartLoading = false, errorAddToCart = null) }
        onDismissSheet()
        showToast()
    }

    private fun onAddToCartError(errorState: ErrorState) {
        updateState { it.copy(isAddToCartLoading = false, errorAddToCart = errorState) }
        showToast()
    }

    override fun onLoginClicked() {
        onDismissSheet()
        sendNewEffect(SearchUiEffect.NavigateToLogin)
    }

    private fun showToast() {
        viewModelScope.launch {
            updateState { it.copy(showToast = true) }
            delay(2000)
            updateState { it.copy(showToast = false) }
            delay(300)
        }
    }

}
