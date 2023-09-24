package presentation.resturantDetails

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Meal
import domain.usecase.IMangeRestaurantDetailsUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class RestaurantScreenModel(
   private val mangeRestaurantDetails: IMangeRestaurantDetailsUseCase,
) : BaseScreenModel<RestaurantUIState, RestaurantUIEffect>(RestaurantUIState()),RestaurantInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope


    init {
        getMostOrders("1")
        getSweets("1")
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

    }

    override fun onBack() {
        sendNewEffect(RestaurantUIEffect.onBack)
    }

    override  fun onGoToDetails() {
        sendNewEffect(RestaurantUIEffect.onGoToDetails)
    }

}