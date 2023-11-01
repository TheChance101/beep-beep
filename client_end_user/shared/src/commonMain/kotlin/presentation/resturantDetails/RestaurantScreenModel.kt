package presentation.resturantDetails

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cuisine
import domain.entity.Meal
import domain.entity.Restaurant
import domain.usecase.IExploreRestaurantUseCase
import domain.usecase.IGetOffersUseCase
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.IManageCartUseCase
import domain.usecase.IManageFavouriteUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class RestaurantScreenModel(
    private val restaurantId: String,
    private val restaurantDetails: IExploreRestaurantUseCase,
    private val manageFavourite: IManageFavouriteUseCase,
    private val manageAuthentication: IManageAuthenticationUseCase,
    private val manageCart: IManageCartUseCase,
    private val manageOffers: IGetOffersUseCase,
) : BaseScreenModel<RestaurantUIState, RestaurantUIEffect>(RestaurantUIState()),
    RestaurantInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        checkIfLoggedIn()
        getRestaurantDetails(restaurantId)
        getCuisinesWithMeals(restaurantId)
    }

    private fun checkIfLoggedIn() {
        tryToExecute(
            { manageAuthentication.getAccessToken() },
            ::onCheckLoginSuccess,
            ::onCheckLoginError
        )
    }

    private fun onCheckLoginSuccess(accessToken: Flow<String>) {
        coroutineScope.launch {
            accessToken.collect { token ->
                if (token.isNotEmpty()) {
                    updateState { it.copy(isLogin = true) }
                } else {
                    updateState { it.copy(isLogin = false) }
                }
            }
        }
    }

    private fun onCheckLoginError(errorState: ErrorState) {
        updateState { it.copy(isLogin = false, error = errorState) }
    }

    private fun getRestaurantDetails(restaurantId: String) {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { restaurantDetails.getRestaurantDetails(restaurantId) },
            ::onGetRestaurantDetailsSuccess,
            ::onGetRestaurantDetailsError
        )

    }

    private fun onGetRestaurantDetailsSuccess(restaurant: Restaurant) {
        updateState { it.copy(restaurantInfo = restaurant.toUIState()) }
        tryToExecute(
            { manageFavourite.checkIfFavoriteRestaurant(restaurantId) },
            ::onGetIfFavoriteRestaurantSuccess,
            ::onError
        )
    }

    private fun onGetRestaurantDetailsError(errorState: ErrorState) {
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


    //region favorite
    private fun onGetIfFavoriteRestaurantSuccess(isFavourite: Boolean) {
        updateState { it.copy(isFavourite = isFavourite) }
    }

    override fun onAddToFavourite() {
        if (state.value.isFavourite) {
            removeFromFavourite(restaurantId)
        } else {
            addToFavourite()
        }
    }

    private fun addToFavourite() {
        tryToExecute(
            { manageFavourite.addRestaurantToFavorites(state.value.restaurantInfo.toRestaurant()) },
            ::onAddToFavouriteSuccess,
            ::onError
        )
    }

    private fun removeFromFavourite(restaurantId: String) {
        tryToExecute(
            { manageFavourite.removeRestaurantFromFavorites(restaurantId) },
            ::onRemoveFromFavouriteSuccess,
            ::onError
        )
    }

    private fun onAddToFavouriteSuccess(isAdded: Boolean) {
        updateState { it.copy(isFavourite = isAdded) }
    }

    private fun onRemoveFromFavouriteSuccess(isRemoved: Boolean) {
        updateState { it.copy(isFavourite = !isRemoved) }
    }

    //endregion

    private fun getCuisinesWithMeals(restaurantId: String) {
        tryToExecute(
            { restaurantDetails.getCuisinesWithMealsInRestaurant(restaurantId) },
            ::onCuisinesWithMealsSuccess,
            ::onError
        )
    }

    private fun onCuisinesWithMealsSuccess(cuisines: List<Cuisine>) {
        updateState { it.copy(cuisines = cuisines.toRestaurantCuisineUiState()) }
    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(error = errorState) }
    }

    override fun onBack() {
        sendNewEffect(RestaurantUIEffect.onBack)
    }

    override fun onGoToDetails(mealId: String) {
        tryToExecute(
            { restaurantDetails.getMealById(mealId) },
            ::onGetMealDetailsSuccess,
            ::onError
        )
    }

    private fun onGetMealDetailsSuccess(meal: Meal) {
        updateState { it.copy(selectedMeal = meal.toUIState()) }
        onShowMealSheet()
    }

    override fun onDismissSheet() {
        state.value.sheetState.dismiss()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
    }

    override fun onShowLoginSheet() {
        coroutineScope.launch {
            state.value.sheetState.dismiss()
            updateState { it.copy(showLoginSheet = true) }
            state.value.sheetState.show()
        }
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
                    quantity = quality,
                    totalPrice = state.value.selectedMeal.price * quality
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

    override fun onShowMealSheet() {
        coroutineScope.launch {
            state.value.sheetState.dismiss()
            updateState { it.copy(showMealSheet = true) }
            state.value.sheetState.show()
        }
    }

    override fun onGoToLogin() {
        sendNewEffect(RestaurantUIEffect.onGoToLogin)
    }

    private suspend fun delayAndChangePermissionSheetState(show: Boolean) {
        delay(300)
        updateState { it.copy(showLoginSheet = show, showMealSheet = show) }
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
