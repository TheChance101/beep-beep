package presentation.restaurants

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class RestaurantScreenModel(
    private val offerId: String,
) : BaseScreenModel<RestaurantsUIState, RestaurantsUIEffect>(RestaurantsUIState()),
    RestaurantsListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    init {

    }


    override fun onBack() {
        sendNewEffect(RestaurantsUIEffect.onBack)
    }

    override fun onRestaurantClicked() {
        TODO("Not yet implemented")
    }


}
