package presentation.resturantDetails

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class RestaurantScreenModel() : BaseScreenModel<RestaurantUIState, RestaurantUIEffect>(RestaurantUIState()),RestaurantInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope
    override fun onAddToFavourite() {

    }

    override fun onBack() {
        sendNewEffect(RestaurantUIEffect.onBack)
    }

    override  fun onGoToDetails() {
        sendNewEffect(RestaurantUIEffect.onGoToDetails)
    }

}