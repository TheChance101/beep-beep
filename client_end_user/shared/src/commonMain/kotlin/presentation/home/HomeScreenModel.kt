package presentation.home

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.DeliveryRide
import domain.entity.FoodOrder
import domain.entity.Offer
import domain.entity.Restaurant
import domain.entity.TaxiRide
import domain.entity.Trip
import domain.entity.TripStatus
import domain.entity.User
import domain.usecase.IExploreRestaurantUseCase
import domain.usecase.IGetOffersUseCase
import domain.usecase.IGetUserLocationUseCase
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.IManageCartUseCase
import domain.usecase.IManageFavouriteUseCase
import domain.usecase.IManageProfileUseCase
import domain.usecase.ITrackOrdersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.cuisines.CuisineUiState
import presentation.cuisines.toCuisineUiState

class HomeScreenModel(
    private val exploreRestaurant: IExploreRestaurantUseCase,
    private val offers: IGetOffersUseCase,
    private val trackOrders: ITrackOrdersUseCase,
    private val manageCart: IManageCartUseCase,
    private val manageFavorite: IManageFavouriteUseCase,
    private val manageProfile: IManageProfileUseCase,
    private val getUserLocation: IGetUserLocationUseCase,
    private val manageAuthentication: IManageAuthenticationUseCase,
) : BaseScreenModel<HomeScreenUiState, HomeScreenUiEffect>(HomeScreenUiState()),
    HomeScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        checkIfLoggedIn()
        getLiveOrders()
        getRecommendedCuisines()
        getNewOffers()
    }

    private fun getLiveOrders() {
        coroutineScope.launch {
            async { getActiveTaxiTrips() }.await()
            async { getActiveDeliveryTrips() }.await()
            async { getActiveFoodOrders() }.await()
        }
    }


    private fun trackingAndUpdateTaxiRide(tripId: String) {
        tryToCollect(
            { trackOrders.trackTaxiRide(tripId) },
            ::onGetTaxiRidesSuccess,
            ::onTrackingError
        )
    }

    private fun trackingAndUpdateDeliveryRide(tripId: String) {
        tryToCollect(
            { trackOrders.trackDeliveryRide(tripId) },
            ::onGetDeliveryRidesSuccess,
            ::onTrackingError
        )
    }

    private fun trackingAndUpdateFoodOrderFromRestaurant(orderId: String) {
        tryToCollect(
            { trackOrders.trackFoodOrderInRestaurant(orderId) },
            ::onGetFoodOrdersSuccess,
            ::onTrackingError
        )
    }

    private fun onGetTaxiRidesSuccess(ride: TaxiRide) {
        updateState { it.copy(isLoading = false) }
        updateState { homeScreenUiState ->
            val currentTaxiRides = homeScreenUiState.liveOrders.taxiRides
            val updatedTaxiRides = currentTaxiRides.mapNotNull { taxiRideUiState ->
                if (taxiRideUiState.tripId == ride.id) {
                    if (ride.tripStatus.statusCode == TripStatus.FINISHED.statusCode) {
                        currentTaxiRides - ride.toTaxiRideUiState()
                        null
                    } else {
                        ride.toTaxiRideUiState()
                    }
                } else {
                    taxiRideUiState
                }
            }
            homeScreenUiState.copy(liveOrders = homeScreenUiState.liveOrders.copy(taxiRides = updatedTaxiRides))
        }
    }

    private fun onGetDeliveryRidesSuccess(ride: DeliveryRide) {
        updateState { it.copy(isLoading = false) }
        updateState { homeScreenUiState ->
            val currentDeliveryRides = homeScreenUiState.liveOrders.deliveryOrders
            val updatedDeliveryRides = currentDeliveryRides.mapNotNull { deliveryRideUiState ->
                if (deliveryRideUiState.tripId == ride.id) {
                    if (ride.tripStatus.statusCode == TripStatus.FINISHED.statusCode) {
                        currentDeliveryRides - ride.toDeliveryOrderUiState()
                        null
                    } else {
                        ride.toDeliveryOrderUiState()
                    }
                } else {
                    deliveryRideUiState
                }
            }
            homeScreenUiState.copy(liveOrders = homeScreenUiState.liveOrders.copy(deliveryOrders = updatedDeliveryRides))
        }
    }


    private fun onGetFoodOrdersSuccess(order: FoodOrder) {
        updateState { it.copy(isLoading = false) }
        updateState { homeScreenUiState ->
            val currentFoodOrders = homeScreenUiState.liveOrders.foodOrders
            val updatedFoodOrders = currentFoodOrders.mapNotNull { foodOrderUiState ->
                if (foodOrderUiState.orderId == order.id) {
                    if (order.orderStatus.statusCode == TripStatus.FINISHED.statusCode) {
                        currentFoodOrders - order.toFoodOrderUiState()
                        null
                    } else {
                        order.toFoodOrderUiState()
                    }
                } else {
                    foodOrderUiState
                }
            }
            homeScreenUiState.copy(liveOrders = homeScreenUiState.liveOrders.copy(foodOrders = updatedFoodOrders))
        }
    }

    private fun getActiveTaxiTrips() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { trackOrders.getActiveTaxiTrips() },
            ::onGetActiveTaxiTripsSuccess,
            ::onTrackingError
        )
    }

    private fun getActiveDeliveryTrips() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { trackOrders.getActiveDeliveryTrips() },
            ::onGetActiveDeliveryTripsSuccess,
            ::onTrackingError
        )
    }

    private fun getActiveFoodOrders() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { trackOrders.getActiveFoodOrders() },
            ::onGetActiveFoodOrdersSuccess,
            ::onTrackingError
        )
    }

    private fun onGetActiveTaxiTripsSuccess(taxiTrips: List<Trip>) {
        updateState { homeScreenState ->
            homeScreenState.copy(
                liveOrders = homeScreenState.liveOrders.copy(
                    taxiRides = taxiTrips.map { trip -> trip.toTaxiRideUiState() }
                )
            )
        }
        val currentTaxiRides = state.value.liveOrders.taxiRides
        if (currentTaxiRides.isNotEmpty()) {
            currentTaxiRides.forEach { taxiRide ->
                trackingAndUpdateTaxiRide(taxiRide.tripId)
            }
        }
    }

    private fun onGetActiveDeliveryTripsSuccess(deliveryTrips: List<DeliveryRide>) {
        updateState { homeScreenState ->
            homeScreenState.copy(
                liveOrders = homeScreenState.liveOrders.copy(
                    deliveryOrders = deliveryTrips.map { trip -> trip.toDeliveryOrderUiState() }
                )
            )
        }

        val currentDeliveryRides = state.value.liveOrders.deliveryOrders
        if (currentDeliveryRides.isNotEmpty()) {
            currentDeliveryRides.forEach { deliveryRide ->
                trackingAndUpdateDeliveryRide(deliveryRide.tripId)
            }
        }
    }

    private fun onGetActiveFoodOrdersSuccess(foodOrders: List<FoodOrder>) {
        updateState { homeScreenState ->
            homeScreenState.copy(
                liveOrders = homeScreenState.liveOrders.copy(
                    foodOrders = foodOrders.map { order -> order.toFoodOrderUiState() }
                )
            )
        }

        val currentFoodOrders = state.value.liveOrders.foodOrders
        if (currentFoodOrders.isNotEmpty()) {
            currentFoodOrders.forEach { order ->
                trackingAndUpdateFoodOrderFromRestaurant(order.orderId)
            }
        }
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
            accessToken.distinctUntilChanged().collectLatest { token ->
                if (token.isNotEmpty()) {
                    updateState { it.copy(isLoggedIn = true) }
                    getUser()
                    getFavoriteRestaurants()
                    checkCartHasMeals()
                } else {
                    updateState { it.copy(isLoggedIn = false, showCart = false) }
                }
            }
        }
    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(isLoggedIn = false) }
    }

    private fun onTrackingError(errorState: ErrorState) {
        updateState { it.copy(isLoading = false) }
    }

    private fun checkCartHasMeals() {
        tryToExecute(
            { manageCart.isCartEmpty() },
            ::onGetCartSuccess,
            ::onGetCartError
        )
    }

    private fun onGetCartSuccess(isCartEmpty: Flow<Boolean>) {
        coroutineScope.launch {
            isCartEmpty.distinctUntilChanged().collectLatest { isEmpty ->
                updateState { it.copy(showCart = !isEmpty) }
            }
        }
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

    override fun onDismissSnackBar() {
        updateState { it.copy(showSnackBar = false) }
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

    override fun onClickActiveFoodOrder(orderId: String, tripId: String) {
        sendNewEffect(HomeScreenUiEffect.NavigateToTrackOrder(orderId, tripId))
    }

    override fun onClickActiveTaxiRide(tripId: String) {
        startTrackUserLocation(tripId)
    }

    private fun startTrackUserLocation(tripId: String) {
        tryToExecute(
            function = getUserLocation::startTracking,
            onSuccess = { onStartTrackUserLocationSuccess(tripId) },
            onError = ::onStartTrackUserLocationError
        )
    }

    private fun onStartTrackUserLocationSuccess(tripId: String) {
        sendNewEffect(HomeScreenUiEffect.NavigateToTrackTaxiRide(tripId))
    }

    private fun onStartTrackUserLocationError(errorState: ErrorState) {
        when (errorState) {
            ErrorState.LocationPermissionDenied -> updateState { it.copy(showSnackBar = true) }
            else -> {}
        }
    }


    private fun getRecommendedCuisines() {
        tryToExecute(
            { exploreRestaurant.getPreferredCuisines().toCuisineUiState() },
            ::onGetCuisinesSuccess,
            ::onGetCuisinesError
        )
    }

    private fun onGetCuisinesSuccess(cuisines: List<CuisineUiState>) {
        updateState { it.copy(recommendedCuisines = cuisines) }
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
            { offers.getNewOffers() },
            ::onGetNewOffersSuccess,
            ::onGetNewOffersError
        )
    }

    private fun onGetNewOffersSuccess(offers: List<Offer>) {
        updateState { it.copy(offers = offers.toUiState()) }
    }

    private fun onGetNewOffersError(error: ErrorState) {
        println("error is $error")
    }
}
