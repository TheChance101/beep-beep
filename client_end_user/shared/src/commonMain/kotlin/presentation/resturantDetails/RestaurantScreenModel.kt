package presentation.resturantDetails

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Meal
import domain.entity.Restaurant
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.IManageFavouriteUseCase
import domain.usecase.IMangeRestaurantDetailsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class RestaurantScreenModel(
   private val mangeRestaurantDetails: IMangeRestaurantDetailsUseCase,
    private val manageFavourite: IManageFavouriteUseCase,
   private val manageAuthentication: IManageAuthenticationUseCase
) : BaseScreenModel<RestaurantUIState, RestaurantUIEffect>(RestaurantUIState()),RestaurantInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope


    init {
        onCheckLogin ()
        getRestaurantDetails("64fa315fb7c56f626e24d852")
        getMostOrders("64fa315fb7c56f626e24d852")
        getSweets("64fa315fb7c56f626e24d852")
    }
    private fun onCheckLogin () {
        tryToExecute(
            { manageAuthentication.getAccessToken() } ,
            ::onCheckLoginSuccess,
            ::onCheckLoginError
        )
    }

    private fun onCheckLoginSuccess (accessToken: String) {
        if(accessToken.isNotEmpty()){
            updateState { it.copy(isLogin = true) }
        }
    }
    private fun onCheckLoginError (errorState: ErrorState) {
        updateState { it.copy( isLogin = false)}
    }

    private fun getRestaurantDetails(restaurantId: String) {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { mangeRestaurantDetails.getRestaurantDetails(restaurantId) },
            ::onGetRestaurantDetailsSuccess,
            ::onGetRestaurantDetailsError
        )

    }

    private  fun onGetRestaurantDetailsSuccess(restaurant: Restaurant) {
        println("AYA2 $restaurant")
        updateState { it.copy(restaurantInfo = restaurant.toUIState()) }
    }

    private fun onGetRestaurantDetailsError(errorState: ErrorState) {
        println("AYA $errorState")
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

   private  fun addToFavourite(restaurantId: String) {
        tryToExecute(
            { manageFavourite.addRestaurantToFavorites(restaurantId) },
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
        println("AYA $isAdded")
        updateState { it.copy(isFavourite = isAdded) }
    }
    private fun onRemoveFromFavouriteSuccess(isAdded: Boolean) {
        println("AYA $isAdded")
        updateState { it.copy(isFavourite = false) }
    }


    private fun getMostOrders(restaurantId: String) {
        tryToExecute(
            { mangeRestaurantDetails.getRestaurantMostOrders(restaurantId) },
            ::onGetMostOrdersSuccess,
            ::onError
        )

    }

    private fun getSweets(restaurantId: String) {
        tryToExecute(
            { mangeRestaurantDetails.getRestaurantSweets(restaurantId) },
            ::onGetSweetsSuccess,
            ::onError
        )
    }

    private  fun onGetMostOrdersSuccess(meals:List<Meal>) {
        updateState { it.copy(mostOrders = meals.map { it.toUIState() }) }
    }

    private  fun onGetSweetsSuccess(meals:List<Meal>) {
        updateState { it.copy(sweets = meals.map { it.toUIState() }) }
    }

    private fun onError(errorState: ErrorState) {
        println("$errorState")
    }



    override fun onAddToFavourite() {
        updateState { it.copy(isFavourite = !state.value.isFavourite) }
        if (state.value.isFavourite) {
            removeFromFavourite("64fa315fb7c56f626e24d852")
        } else {
            addToFavourite("64fa315fb7c56f626e24d852")
        }

    }

    override fun onBack() {
        sendNewEffect(RestaurantUIEffect.onBack)
    }

    override  fun onGoToDetails(mealId: String) {
        onShowMealSheet()
        tryToExecute(
            { mangeRestaurantDetails.getMealById(mealId) },
            ::onGetMealDetailsSuccess,
            ::onError
        )
    }
    private fun onGetMealDetailsSuccess(meal: Meal) {
        updateState { it.copy(meal = meal.toUIState()) }

    }
    override fun onDismissSheet() {

        state.value.sheetState.dismiss()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
    }

    override fun onShowSheet() {
        updateState { it.copy(showLoginSheet = true) }
        coroutineScope.launch {
            state.value.sheetState.dismiss()
            delayAndChangePermissionSheetState(true)
            state.value.sheetState.show()
        }
    }

    override fun onGoToCart() {

    }

    override  fun onShowMealSheet() {
        updateState { it.copy(showMealSheet = true) }

        coroutineScope.launch {
            state.value.sheetState.dismiss()
            delayAndChangePermissionSheetState(true)
            state.value.sheetState.show()
        }
    }

    override fun onGoToLogin() {
        sendNewEffect(RestaurantUIEffect.onGoToLogin)
    }

    override fun onIncressQuantity() {
        updateState { it.copy(
            meal = state.value.meal.copy(
                quantity = state.value.meal.quantity + 1,
                price = state.value.meal.price * state.value.meal.quantity
            )
        ) }
    }

    override fun onDecressQuantity() {
        if(state.value.meal.quantity == 1) return
        updateState { it.copy(
            meal = state.value.meal.copy(
                quantity = state.value.meal.quantity - 1,
                price = state.value.meal.price * state.value.meal.quantity
            )
        ) }
    }

    private suspend fun delayAndChangePermissionSheetState(show: Boolean) {
        delay(300)
        updateState { it.copy(showLoginSheet = show,showMealSheet=show) }
    }
}