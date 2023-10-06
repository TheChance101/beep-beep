package presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAppBar
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.auth.login.LoginScreen
import presentation.base.BaseScreen
import presentation.cart.CartScreen
import presentation.composable.BpImageLoader
import presentation.composable.ImageSlider
import presentation.composable.ItemSection
import presentation.composable.SectionHeader
import presentation.composable.modifier.roundedBorderShape
import presentation.cuisines.CuisinesScreen
import presentation.home.composable.CartCard
import presentation.home.composable.ChatSupportCard
import presentation.home.composable.CuisineCard
import presentation.home.composable.OrderCard
import resources.Resources
import util.root

class HomeScreen :
    BaseScreen<HomeScreenModel, HomeScreenUiState, HomeScreenUiEffect, HomeScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: HomeScreenUiEffect, navigator: Navigator) {
        when (effect) {
            is HomeScreenUiEffect.NavigateToCuisineDetails -> println("Cuisine id ${effect.cuisineId}")
            is HomeScreenUiEffect.NavigateToCuisines -> navigator.root?.push(CuisinesScreen())
            is HomeScreenUiEffect.NavigateToChatSupport -> println("Navigate to Chat support screen")
            is HomeScreenUiEffect.NavigateToOrderTaxi -> println("Navigate to Order Taxi screen")
            is HomeScreenUiEffect.ScrollDownToRecommendedRestaurants -> println("Scroll down home screen")
            is HomeScreenUiEffect.NavigateToOfferItem -> println("Navigate to offer item details ${effect.offerId}")
            is HomeScreenUiEffect.NavigateToSearch -> println("Navigate to Search Screen")
            is HomeScreenUiEffect.NavigateToOrderDetails ->  println("Navigate to order details ${effect.orderId}")
            is HomeScreenUiEffect.NavigateToCart -> navigator.root?.push(CartScreen())
            is HomeScreenUiEffect.NavigateLoginScreen -> navigator.root?.push(LoginScreen())
        }
    }

    @OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class,
        ExperimentalMaterial3Api::class
    )
    @Composable
    override fun onRender(state: HomeScreenUiState, listener: HomeScreenInteractionListener) {
        val painters = mutableListOf<Painter>()
        repeat(state.favoriteRestaurants.size) {
            painters.add(painterResource(Resources.images.placeholder))
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Theme.colors.background),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            stickyHeader {
                BpAppBar(
                    title = if (state.user.isLogin) {
                        Resources.strings.welcome + " ${state.user.username}"
                    } else {
                        Resources.strings.loginWelcomeMessage
                    },
                    actions = {
                        if (state.user.isLogin) {
                            Wallet(value = state.user.currency + state.user.wallet)
                        } else {
                            BpButton(
                                modifier = Modifier.heightIn(max = 32.dp).padding(end = 16.dp),
                                textPadding = PaddingValues(horizontal = 16.dp),
                                title = Resources.strings.login,
                                onClick = listener::onLoginClicked
                            )
                        }
                    }
                )
            }

            item {
                ImageSlider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onItemClickListener = { listener.onClickOffersSlider(it) },
                    images = state.getOfferImages()
                )
            }

            item {
                BpSimpleTextField(
                    "",
                    hint = Resources.strings.searchHint,
                    hintColor = Theme.colors.contentSecondary,
                    onValueChange = {},
                    onClick = { listener.onClickSearch() },
                    leadingPainter = painterResource(Resources.images.searchOutlined),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                CartCard(onClick = { listener.onClickCartCard() })
            }

            if (state.hasProgress) {
                item {
                    Text(
                        text = Resources.strings.inProgress,
                        style = Theme.typography.titleLarge.copy(Theme.colors.contentPrimary),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                items(state.inProgressWrapper.taxisOnTheWay) {
                    HorizontalImageCard(
                        painter = painterResource(Resources.images.taxiOnTheWay),
                        titleText = Resources.strings.taxiOnTheWay,
                    ) { textStyle ->
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = it.color, style = textStyle)
                            Circle()
                            Text(text = it.plate, style = textStyle)
                            Circle()
                            Text(
                                text = "${it.timeToArriveInMints} min to arrive",
                                style = textStyle
                            )
                        }
                    }
                }
                items(state.inProgressWrapper.tripsOnTheWay) {
                    HorizontalImageCard(
                        painter = painterResource(Resources.images.taxiOnTheWay),
                        titleText = Resources.strings.enjoyYourRide,
                        titleTextColor = Theme.colors.contentSecondary,
                    ) { textStyle ->
                        Text(
                            text = "${it.timeToArriveInMints} min to arrive",
                            style = textStyle
                        )
                    }
                }
                items(state.inProgressWrapper.ordersOnTheWay) {
                    HorizontalImageCard(
                        painter = painterResource(Resources.images.orderOnTheWay),
                        titleText = Resources.strings.orderOnTheWay,
                    ) { textStyle ->
                        Text(
                            text = "From ${it.restaurantName}",
                            style = textStyle
                        )
                    }
                }
            }

            item {
                ChatSupportCard(
                    onClick = listener::onClickChatSupport,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OrderCard(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        onClick = listener::onClickOrderTaxi,
                        buttonTitle = Resources.strings.orderTaxiButtonTitle,
                        painter = painterResource(Resources.images.orderTaxi)
                    )
                    OrderCard(
                        modifier = Modifier.fillMaxWidth(1f),
                        onClick = listener::onClickOrderFood,
                        buttonTitle = Resources.strings.orderFoodButtonTitle,
                        painter = painterResource(Resources.images.orderImage),
                        color = Theme.colors.orange
                    )
                }
            }
            item {
                LastOrder(state.lastOrder,listener)
            }
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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
                )
            }
            item {
                ItemSection(
                    header = Resources.strings.eidSpecials,
                    titles = state.favoriteRestaurants.map { it.name },
                    ratings = state.favoriteRestaurants.map { it.rating },
                    priceLevels = state.favoriteRestaurants.map { it.priceLevel },
                    painters = painters,
                    modifier = Modifier.padding(top = 16.dp),
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
                    modifier = Modifier.padding(top = 16.dp),
                    hasDeliveryPrice = true,
                    deliveryPrices = listOf("Free", "Free", "Free")
                )
            }
        }
    }

    @Composable
    private fun HorizontalImageCard(
        painter: Painter,
        titleText: String,
        modifier: Modifier = Modifier,
        titleTextColor: Color = Theme.colors.primary,
        titleTextStyle: TextStyle = Theme.typography.title.copy(color = titleTextColor),
        captionText: @Composable (TextStyle) -> Unit,
    ) {
        Row(
            modifier = modifier.heightIn(min = 72.dp).fillMaxWidth().padding(horizontal = 16.dp)
                .roundedBorderShape().padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = titleText,
                    style = titleTextStyle,
                    color = titleTextColor
                )
                captionText(Theme.typography.caption.copy(color = Theme.colors.contentSecondary))
            }
        }
    }

    @Composable
    private fun Circle(
        modifier: Modifier = Modifier,
        circleSize: Dp = 4.dp,
        circleColor: Color = Theme.colors.disable
    ) {
        Spacer(modifier.size(circleSize).drawBehind { drawCircle(circleColor) })
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun LastOrder(order: OrderUiState, listener: HomeScreenInteractionListener) {
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(Resources.strings.lastOrder, style = Theme.typography.titleLarge.copy(color = Theme.colors.contentPrimary))
            Row(modifier = Modifier.fillMaxWidth().height(80.dp).padding(top = 8.dp)) {
                BpImageLoader(
                    modifier = Modifier.fillMaxHeight().width(104.dp).clip(RoundedCornerShape(8.dp)),
                    imageUrl = order.image
                )
                Column(modifier = Modifier.padding(8.dp).fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                    Text(order.restaurantName, style = Theme.typography.title.copy(color = Theme.colors.contentPrimary))
                    Text(order.date, style = Theme.typography.body.copy(color = Theme.colors.contentSecondary))
                    Row(modifier = Modifier.clickable { listener.onClickOrderAgain(order.id) }, verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = Resources.strings.orderAgain,
                            style = Theme.typography.body,
                            color = Theme.colors.primary
                        )
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(Resources.images.arrowRight),
                            contentDescription = Resources.strings.seeAllDescription,
                            tint = Theme.colors.primary
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun Wallet(
        value: String,
        modifier: Modifier = Modifier,
    ) {
        Column(modifier = modifier.padding(end = 16.dp)) {
            Text(
                Resources.strings.wallet,
                style = Theme.typography.body,
                color = Theme.colors.contentSecondary
            )
            Text(
                value,
                style = Theme.typography.title,
                color = Theme.colors.primary
            )
        }
    }
}