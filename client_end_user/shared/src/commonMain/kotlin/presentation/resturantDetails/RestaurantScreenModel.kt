package presentation.resturantDetails

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Meal
import domain.entity.Restaurant
import domain.usecase.IManageFavouriteUseCase
import domain.usecase.IMangeRestaurantDetailsUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class RestaurantScreenModel(
   private val mangeRestaurantDetails: IMangeRestaurantDetailsUseCase,
    private val manageFavourite: IManageFavouriteUseCase,
) : BaseScreenModel<RestaurantUIState, RestaurantUIEffect>(RestaurantUIState()),RestaurantInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope


    init {
        getRestaurantDetails("64fa315fb7c56f626e24d852")
        getMostOrders("64fa315fb7c56f626e24d852")
        getSweets("64fa315fb7c56f626e24d852")
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

    override  fun onGoToDetails() {
        sendNewEffect(RestaurantUIEffect.onGoToDetails)
    }

}