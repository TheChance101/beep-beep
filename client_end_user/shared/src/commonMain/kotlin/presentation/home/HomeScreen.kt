package presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
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
        Column(modifier = Modifier.fillMaxSize().background(Theme.colors.background)) {

            ImageSlider(
                modifier = Modifier.padding(16.dp),
                onItemClickListener = { listener.onClickOffersSlider(it) },
                images = state.getOfferImages()
            )

            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                ChatSupportCard(onClick = listener::onClickChatSupport)

                Row(
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

                SectionHeader(
                    onClickViewAll = listener::onclickSeeAllCuisines,
                    title = Resources.strings.cuisineSectionTitle,
                    showViewAll = true
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
    }
}