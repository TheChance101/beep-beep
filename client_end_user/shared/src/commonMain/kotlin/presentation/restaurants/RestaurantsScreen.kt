package presentation.restaurants

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.parameter.parametersOf
import presentation.base.BaseScreen


data class RestaurantsScreen(val offerId: String? = null) :
    BaseScreen<RestaurantsScreenModel, RestaurantsUIState, RestaurantsUIEffect, RestaurantsListener>() {

    override fun onEffect(effect: RestaurantsUIEffect, navigator: Navigator) {

    }

    @Composable
    override fun Content() {
        initScreen(getScreenModel(parameters = { parametersOf(offerId) }))
    }

    @Composable
    override fun onRender(state: RestaurantsUIState, listener: RestaurantsListener) {

    }
}




