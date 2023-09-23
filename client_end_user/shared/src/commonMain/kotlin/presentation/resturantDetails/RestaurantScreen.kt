package presentation.resturantDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import resources.Resources

object RestaurantScreen :
    BaseScreen<RestaurantScreenModel, RestaurantUIState, RestaurantUIEffect, RestaurantInteractionListener>() {


    override fun onEffect(effect: RestaurantUIEffect, navigator: Navigator) {
        when(effect){
            is RestaurantUIEffect.onBack -> navigator.pop()
            is RestaurantUIEffect.onGoToDetails -> {}
        }
    }

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: RestaurantUIState, listener: RestaurantInteractionListener) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Resources.images.placeholder),
                contentDescription = "background"
            )
        }
    }
}