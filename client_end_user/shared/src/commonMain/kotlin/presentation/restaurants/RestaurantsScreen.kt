package presentation.restaurants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAppBar
import com.beepbeep.designSystem.ui.composable.BpImageLoader
import com.beepbeep.designSystem.ui.composable.BpPagingList
import com.beepbeep.designSystem.ui.composable.modifier.noRippleEffect
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf
import presentation.base.BaseScreen
import presentation.composable.BpPriceLevel
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
            Modifier
                .fillMaxSize()
                .background(Theme.colors.background)
        ) {
            BpAppBar(
                title = Resources.strings.restaurants,
                onNavigateUp = listener::onBackClicked,
                painterResource = painterResource(Resources.images.arrowLeft)
            )
            BpPagingList(data = restaurants, bottomPadding = getNavigationBarPadding()) { restaurant ,modifier->
                restaurant?.let {
                    Restaurant(
                        modifier = modifier,
                        restaurant = restaurant,
                        onClickCard = listener::onRestaurantClicked
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun Restaurant(
        restaurant: RestaurantUIState,
        onClickCard: (String) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Card(
            modifier = modifier.fillMaxWidth().noRippleEffect { onClickCard(restaurant.id) },
            colors = CardDefaults.cardColors(Color.Transparent),
            shape = RoundedCornerShape(0.dp)
        ) {
            BpImageLoader(
                modifier = Modifier.height(156.dp).clip(RoundedCornerShape(8.dp)),
                imageUrl = restaurant.imageUrl,
                errorPlaceholderImageUrl = Resources.images.restaurantErrorPlaceholder,
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp).width(232.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = restaurant.name,
                    style = Theme.typography.title,
                    color = Theme.colors.contentPrimary
                )
                Spacer(Modifier.weight(1f))

                if (restaurant.rate != null) {
                    Image(
                        modifier = Modifier.padding(end = 4.dp).size(16.dp),
                        painter = painterResource(Resources.images.filledStar),
                        contentDescription = null
                    )
                    Text(
                        text = restaurant.rate.toString(),
                        modifier = Modifier.padding(end = 4.dp),
                        style = Theme.typography.caption,
                        color = Theme.colors.contentSecondary
                    )

                    Spacer(
                        modifier.padding(top = 16.dp, end = 4.dp).clip(CircleShape).size(4.dp)
                            .background(Theme.colors.disable)

                    )
                }
                BpPriceLevel(restaurant.priceLevel)
            }
        }

    }
}



