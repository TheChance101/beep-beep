package presentation.restaurants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAppBar
import com.beepbeep.designSystem.ui.composable.BpPagingList
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf
import presentation.auth.login.LoginScreen
import presentation.base.BaseScreen
import presentation.composable.BottomSheet
import presentation.composable.MealBottomSheet
import presentation.composable.MealCard
import presentation.composable.ModalBottomSheetState
import presentation.composable.modifier.noRippleEffect
import presentation.meals.MealsUiEffect
import presentation.resturantDetails.Composable.NeedToLoginSheet
import presentation.resturantDetails.Composable.ToastMessage
import presentation.resturantDetails.RestaurantScreen
import resources.Resources
import util.getNavigationBarPadding


data class RestaurantsScreen(val offerId: String? = null) :
    BaseScreen<RestaurantsScreenModel, RestaurantsUIState, RestaurantsUIEffect, RestaurantsListener>() {

    override fun onEffect(effect: RestaurantsUIEffect, navigator: Navigator) {
        when (effect) {
            is RestaurantsUIEffect.NavigateBack -> navigator.pop()
            is RestaurantsUIEffect.NavigateToRestaurantDetails -> {
                navigator.push(RestaurantScreen(effect.id))
            }
        }
    }

    @Composable
    override fun Content() {
        initScreen(getScreenModel(parameters = { parametersOf(offerId) }))
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: RestaurantsUIState, listener: RestaurantsListener) {
        val restaurants = state.restaurants.collectAsLazyPagingItems()
        Column(
            Modifier.fillMaxSize().background(Theme.colors.background).padding(
                getNavigationBarPadding()
            )
        ) {
            BpAppBar(
                title = Resources.strings.restaurants,
                onNavigateUp = listener::onBackClicked,
                painterResource = painterResource(Resources.images.arrowLeft)
            )
            BpPagingList(data = restaurants) { restaurant ->
                restaurant?.let {

                }
            }
        }
    }
}




