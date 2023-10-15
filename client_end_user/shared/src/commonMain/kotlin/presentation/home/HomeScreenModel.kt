package presentation.home

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cart
import domain.entity.InProgressWrapper
import domain.entity.Restaurant
import domain.entity.User
import domain.usecase.IInProgressTrackerUseCase
import domain.usecase.IManageCartUseCase
import domain.usecase.IManageFavouriteUseCase
import domain.usecase.IGetOffersUseCase
import domain.usecase.IManageSettingUseCase
import domain.usecase.IExploreRestaurantUseCase
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.IManageProfileUseCase
import domain.usecase.ManageProfileUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.cuisines.CuisineUiState
import presentation.cuisines.toCuisineUiState

class HomeScreenModel(
    private val exploreRestaurant: IExploreRestaurantUseCase,
    private val offers: IGetOffersUseCase,
    private val inProgressTrackerUseCase: IInProgressTrackerUseCase,
    private val manageCart: IManageCartUseCase,
    private val manageFavorite: IManageFavouriteUseCase,
    private val manageProfile: IManageProfileUseCase,
    private val manageAuthentication: IManageAuthenticationUseCase
) : BaseScreenModel<HomeScreenUiState, HomeScreenUiEffect>(HomeScreenUiState()),
    HomeScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        checkIfLoggedIn()
        getInProgress()
        getRecommendedCuisines()
        getNewOffers()
    }

    private fun checkIfLoggedIn() {
        tryToExecute(
            { manageAuthentication.getAccessToken() },
            ::onCheckIfLoggedInSuccess,
            ::onError
        )
    }

    private fun onCheckIfLoggedInSuccess(accessToken: Flow<String>) {
        coroutineScope.launch {
            accessToken.collect { token ->
                if (token.isNotEmpty()) {
                    updateState { it.copy(isLoggedIn = true) }
                    getUser()
                    getFavoriteRestaurants()
                    checkIfThereIsOrderInCart()
                } else {
                    updateState { it.copy(isLoggedIn = false, showCart = false) }
                }
            }
        }
    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(isLoggedIn = false) }
    }


    private fun checkIfThereIsOrderInCart() {
        tryToExecute(
            { manageCart.getCart() },
            ::onGetCartSuccess,
            ::onGetCartError
        )
    }

    private fun onGetCartSuccess(cart: Cart) {
        updateState { it.copy(showCart = !cart.meals.isNullOrEmpty()) }
    }

    private fun onGetCartError(errorState: ErrorState) {
        updateState { it.copy(showCart = false) }
    }

    private fun getUser() {
        tryToExecute(
            { manageProfile.getUserProfile() },
            ::onGetUserSuccess,
            ::onGetUserError
        )
    }

    private fun onGetUserError(errorState: ErrorState) {
        updateState { it.copy(isLoggedIn = false) }
    }

    private fun onGetUserSuccess(user: User) {
        updateState { it.copy(user = user.toUIState()) }
    }

    private fun getInProgress() {
        tryToExecute(
            { inProgressTrackerUseCase.getInProgress() },
            ::onGetInProgressSuccess,
            ::onGetCuisinesError
        )
    }

    private fun onGetInProgressSuccess(inProgressWrapper: InProgressWrapper) {
        updateState { it.copy(inProgressWrapper = inProgressWrapper) }
    }

    override fun onClickCuisineItem(cuisineId: String) {
        val cuisine = state.value.recommendedCuisines.first { it.cuisineId == cuisineId }
        sendNewEffect(HomeScreenUiEffect.NavigateToMeals(cuisineId, cuisine.cuisineName))
    }

    override fun onClickSeeAllCuisines() {
        sendNewEffect(HomeScreenUiEffect.NavigateToCuisines)
    }

    override fun onClickChatSupport() {
        sendNewEffect(HomeScreenUiEffect.NavigateToChatSupport)
    }

    override fun onClickOrderTaxi() {
        sendNewEffect(HomeScreenUiEffect.NavigateToOrderTaxi)
    }

    override fun onClickOrderFood() {
        sendNewEffect(HomeScreenUiEffect.ScrollDownToRecommendedRestaurants)
    }

    override fun onClickOffersSlider(position: Int) {
        coroutineScope.launch {
            val id = offers.getNewOffers()[position].id
            sendNewEffect(HomeScreenUiEffect.NavigateToOfferItem(id))
        }
    }


    override fun onClickOrderAgain(orderId: String) {
        sendNewEffect(HomeScreenUiEffect.NavigateToOrderDetails(orderId))
    }

    override fun onLoginClicked() {
        sendNewEffect(HomeScreenUiEffect.NavigateLoginScreen)
    }

    override fun onClickCartCard() {
        sendNewEffect(HomeScreenUiEffect.NavigateToCart)
    }

    override fun onClickRestaurantCard(restaurantId: String) {
        sendNewEffect(HomeScreenUiEffect.NavigateToRestaurantDetails(restaurantId))
    }

    override fun onClickInProgressOrderCard(orderId: String) {
        sendNewEffect(HomeScreenUiEffect.NavigateToTrackOrderFood(orderId))
    }

    override fun onClickInProgressTaxiRide(tripId: String) {
        sendNewEffect(HomeScreenUiEffect.NavigateToTrackTaxiRide(tripId))
    }

    private fun getRecommendedCuisines() {
        tryToExecute(
            { exploreRestaurant.getCuisines().toCuisineUiState() },
            ::onGetCuisinesSuccess,
            ::onGetCuisinesError
        )
    }

    private fun onGetCuisinesSuccess(cuisines: List<CuisineUiState>) {
        val popularCuisines = cuisines.shuffled().take(state.value.maxCuisinesInHome)
        updateState {
            it.copy(
                recommendedCuisines = popularCuisines,
                isMoreCuisine = cuisines.size > it.maxCuisinesInHome
            )
        }
    }

    private fun onGetCuisinesError(error: ErrorState) {
        println("error is $error")
    }

    private fun getFavoriteRestaurants() {
        tryToExecute(
            { manageFavorite.getFavoriteRestaurants() },
            ::onGetFavoriteRestaurantsSuccess,
            ::onGetFavoriteRestaurantsError
        )
    }

    private fun onGetFavoriteRestaurantsSuccess(restaurants: List<Restaurant>) {
        updateState { it.copy(favoriteRestaurants = restaurants.toRestaurantUiState()) }
    }

    private fun onGetFavoriteRestaurantsError(error: ErrorState) {
        println("error is $error")
    }


    private fun getNewOffers() {
        tryToExecute(
            { offers.getNewOffers().map { it.toUiState() } },
            ::onGetNewOffersSuccess,
            ::onGetNewOffersError
        )
    }

    private fun onGetNewOffersSuccess(offers: List<OfferUiState>) {
        updateState { it.copy(offers = offers) }
    }

    private fun onGetNewOffersError(error: ErrorState) {
        println("error is $error")
    }
}
