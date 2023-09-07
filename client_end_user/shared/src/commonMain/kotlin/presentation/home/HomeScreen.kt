package presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composable.ItemSection
import presentation.composable.ImageSlider
import presentation.composable.SectionHeader
import presentation.home.composable.ChatSupportCard
import presentation.home.composable.CuisineCard
import presentation.home.composable.OrderCard
import resources.Resources

class HomeScreen :
    BaseScreen<HomeScreenModel, HomeScreenUiState, HomeScreenUiEffect, HomeScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: HomeScreenUiEffect, navigator: Navigator) {
        when (effect) {
            is HomeScreenUiEffect.NavigateToCuisineDetails -> println("Cuisine id ${effect.cuisineId}")
            is HomeScreenUiEffect.NavigateToCuisines -> println("Navigate to Cuisine Screen")
            is HomeScreenUiEffect.NavigateToChatSupport -> println("Navigate to Chat support screen")
            is HomeScreenUiEffect.NavigateToOrderTaxi -> println("Navigate to Order Taxi screen")
            is HomeScreenUiEffect.ScrollDownToRecommendedRestaurants -> println("Scroll down home screen")
            is HomeScreenUiEffect.NavigateToOfferItem -> println("Navigate to offer item details ${effect.offerId}")
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: HomeScreenUiState, listener: HomeScreenInteractionListener) {
        val painters = mutableListOf<Painter>()
        repeat(state.favoriteRestaurants.size) {
            painters.add(painterResource(Resources.images.placeholder))
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Theme.colors.background),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item{
                ImageSlider(
                    modifier = Modifier.padding(16.dp),
                    onItemClickListener = { listener.onClickOffersSlider(it) },
                    images = state.getOfferImages()
                )
            }

            item {
                ChatSupportCard(
                    onClick = listener::onClickChatSupport,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item {
                Row(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OrderCard(
                        onClick = listener::onClickOrderTaxi,
                        buttonTitle = Resources.strings.orderTaxiButtonTitle,
                        painter = painterResource(Resources.images.orderTaxi)
                    )
                    OrderCard(
                        onClick = listener::onClickOrderFood,
                        buttonTitle = Resources.strings.orderFoodButtonTitle,
                        painter = painterResource(Resources.images.orderImage),
                        color = Theme.colors.orange
                    )
                }
            }
            item {
                Column {
                    SectionHeader(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 8.dp
                        ),
                        onClickViewAll = listener::onclickSeeAllCuisines,
                        title = Resources.strings.cuisineSectionTitle,
                        showViewAll = true
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        state.recommendedCuisines.forEach { cuisine ->
                            CuisineCard(
                                modifier = Modifier,
                                cuisine = cuisine,
                                onClickCuisine = listener::onClickCuisineItem
                            )
                        }
                    }
                }
            }
            item {
                ItemSection(
                    header = Resources.strings.favoriteRestaurants,
                    titles = state.favoriteRestaurants.map { it.name },
                    ratings = state.favoriteRestaurants.map { it.rating },
                    priceLevels = state.favoriteRestaurants.map { it.priceLevel },
                    painters = painters,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
            item {
                ItemSection(
                    header = Resources.strings.eidSpecials,
                    titles = state.favoriteRestaurants.map { it.name },
                    ratings = state.favoriteRestaurants.map { it.rating },
                    priceLevels = state.favoriteRestaurants.map { it.priceLevel },
                    painters = painters,
                    modifier = Modifier.padding(vertical = 16.dp),
                    hasOffer = true,
                    offers = listOf("15 %", "15 %", "15 %")
                )
            }

            item {
                ItemSection(
                    header = Resources.strings.freeDelivery,
                    titles = state.favoriteRestaurants.map { it.name },
                    ratings = state.favoriteRestaurants.map { it.rating },
                    priceLevels = state.favoriteRestaurants.map { it.priceLevel },
                    painters = painters,
                    modifier = Modifier.padding(vertical = 16.dp),
                    hasDeliveryPrice = true,
                    deliveryPrices = listOf("Free", "Free", "Free")
                )
            }
        }
    }
}