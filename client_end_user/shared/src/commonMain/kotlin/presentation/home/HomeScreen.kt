package presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.beepbeep.designSystem.ui.composable.BPSnackBar
import com.beepbeep.designSystem.ui.composable.BpAppBar
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.theme.Theme
import domain.entity.FoodOrder
import domain.entity.TripStatus
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.auth.login.LoginScreen
import presentation.base.BaseScreen
import presentation.cart.CartScreen
import presentation.chatSupport.ChatSupportScreen
import presentation.composable.BpImageLoader
import presentation.composable.ImageSlider
import presentation.composable.ItemSection
import presentation.composable.SectionHeader
import presentation.composable.modifier.noRippleEffect
import presentation.composable.modifier.roundedBorderShape
import presentation.cuisines.CuisineUiState
import presentation.cuisines.CuisinesScreen
import presentation.home.composable.CartCard
import presentation.home.composable.ChatSupportCard
import presentation.home.composable.Circle
import presentation.home.composable.CuisineCard
import presentation.home.composable.OrderCard
import presentation.main.SearchTab
import presentation.meals.MealsScreen
import presentation.orderFoodTracking.OrderFoodTrackingScreen
import presentation.resturantDetails.RestaurantScreen
import resources.Resources
import util.getNavigationBarPadding
import util.root

class HomeScreen : BaseScreen<
        HomeScreenModel,
        HomeScreenUiState,
        HomeScreenUiEffect,
        HomeScreenInteractionListener
        >() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: HomeScreenUiEffect, navigator: Navigator) {

        when (effect) {
            is HomeScreenUiEffect.NavigateToMeals -> {
                navigator.root?.push(MealsScreen(effect.cuisineId, effect.cuisineName))
            }

            is HomeScreenUiEffect.NavigateToCuisines -> navigator.root?.push(CuisinesScreen())
            is HomeScreenUiEffect.NavigateToChatSupport -> navigator.root?.push(ChatSupportScreen())
            is HomeScreenUiEffect.NavigateToOrderTaxi -> println("Navigate to Order Taxi screen")
            is HomeScreenUiEffect.ScrollDownToRecommendedRestaurants -> println("Scroll down home screen")
            is HomeScreenUiEffect.NavigateToOfferItem -> println("Navigate to offer item details ${effect.offerId}")
            is HomeScreenUiEffect.NavigateToOrderDetails -> println("Navigate to order details ${effect.orderId}")
            is HomeScreenUiEffect.NavigateToCart -> navigator.root?.push(CartScreen())
            is HomeScreenUiEffect.NavigateLoginScreen -> navigator.root?.push(LoginScreen())
            is HomeScreenUiEffect.NavigateToRestaurantDetails -> navigator.root?.push(
                RestaurantScreen(effect.restaurantId)
            )

            is HomeScreenUiEffect.NavigateToTrackOrder -> navigator.root?.push(
                OrderFoodTrackingScreen(effect.orderId, effect.tripId)
            )

            is HomeScreenUiEffect.NavigateToTrackTaxiRide -> println("navigate to track taxi ride ${effect.tripId}")
        }
    }

    @OptIn(
        ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
        ExperimentalResourceApi::class
    )
    @Composable
    override fun onRender(state: HomeScreenUiState, listener: HomeScreenInteractionListener) {
        val tabNavigator = LocalTabNavigator.current
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Theme.colors.background),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {

            stickyHeader {
                BpAppBar(
                    title = if (state.isLoggedIn) {
                        Resources.strings.welcome + " ${state.user.username}"
                    } else {
                        Resources.strings.loginWelcomeMessage
                    },
                    actions = {
                        if (state.isLoggedIn) {
                            Wallet(value = "${state.user.currency} ${state.user.wallet}")
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

            this.search(onClick = { tabNavigator.current = SearchTab })

            item {
                AnimatedVisibility(state.showCart) {
                    CartCard(onClick = { listener.onClickCartCard() })
                }
            }

            this.inProgressSection(state, listener)

            item {
                AnimatedVisibility(state.isLoggedIn) {
                    ChatSupportCard(
                        onClick = listener::onClickChatSupport,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            this.orderSection(
                onClickOrderFood = listener::onClickOrderFood,
                onClickOrderTaxi = listener::onClickOrderTaxi
            )

            item {
                AnimatedVisibility(state.isThereLastOrder) {
                    LastOrder(state.lastOrder, listener)
                }
            }

            item {
                Cuisines(
                    recommendedCuisines = state.recommendedCuisines,
                    onClickCuisineItem = listener::onClickCuisineItem,
                    onClickSeeAllCuisines = listener::onClickSeeAllCuisines,
                    showSeeAllCuisine = state.isMoreCuisine
                )
            }

            item {
                AnimatedVisibility(state.isLoggedIn && state.favoriteRestaurants.isNotEmpty()) {
                    ItemSection(
                        { restaurantId -> listener.onClickRestaurantCard(restaurantId) },
                        header = Resources.strings.favoriteRestaurants,
                        ids = state.favoriteRestaurants.map { it.id },
                        titles = state.favoriteRestaurants.map { it.name },
                        ratings = state.favoriteRestaurants.map { it.rating },
                        priceLevels = state.favoriteRestaurants.map { it.priceLevel },
                        imageUrls = state.favoriteRestaurants.map { it.imageUrl },
                        hasRating = true,
                        hasPriceLevel = true,
                    )
                }
            }

            this.offers(
                offers = state.offers,
                onRestaurantClicked = listener::onClickRestaurantCard
            )

            item {
                BPSnackBar(
                    icon = painterResource(Resources.images.warningIcon),
                    iconBackgroundColor = Theme.colors.warningContainer,
                    iconTint = Theme.colors.warning,
                    isVisible = state.showSnackBar,
                    modifier = Modifier.padding(bottom = getNavigationBarPadding().calculateBottomPadding())
                ) {
                    Text(
                        text = Resources.strings.accessDeniedMessage,
                        style = Theme.typography.body.copy(color = Theme.colors.contentPrimary),
                    )
                }
            }
        }
    }

    private fun LazyListScope.offers(
        offers: List<OfferUiState>,
        onRestaurantClicked: (String) -> Unit,
    ) {
        offers.forEach { offer ->
            this.item {
                ItemSection(
                    onRestaurantClicked,
                    header = offer.title,
                    ids = offer.restaurants.map { it.id },
                    titles = offer.restaurants.map { it.name },
                    ratings = offer.restaurants.map { it.rating },
                    priceLevels = offer.restaurants.map { it.priceLevel },
                    imageUrls = offer.restaurants.map { it.imageUrl },
                    hasRating = true,
                    hasPriceLevel = true,
                )
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun LazyListScope.orderSection(
        onClickOrderTaxi: () -> Unit,
        onClickOrderFood: () -> Unit,
    ) {
        this.item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OrderCard(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    onClick = onClickOrderTaxi,
                    buttonTitle = Resources.strings.orderTaxiButtonTitle,
                    painter = painterResource(Resources.images.orderTaxi)
                )
                OrderCard(
                    modifier = Modifier.fillMaxWidth(1f),
                    onClick = onClickOrderFood,
                    buttonTitle = Resources.strings.orderFoodButtonTitle,
                    painter = painterResource(Resources.images.orderImage),
                    color = Theme.colors.orange
                )
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun LazyListScope.inProgressSection(
        state: HomeScreenUiState,
        listener: HomeScreenInteractionListener,
    ) {
        if (state.hasLiveOrders && state.isLoggedIn) {
            item {
                Text(
                    text = Resources.strings.inProgress,
                    style = Theme.typography.titleLarge.copy(Theme.colors.contentPrimary),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // taxi rides
            items(
                items = state.liveOrders.taxiRides,
                key = { it.tripId }) { taxiRideUiState ->
                InProgressCard(
                    painter = painterResource(Resources.images.taxiOnTheWay),
                    titleText = if (taxiRideUiState.rideStatus == TripStatus.APPROVED.statusCode) {
                        Resources.strings.taxiOnTheWay
                    } else {
                        Resources.strings.enjoyYourRide
                    },
                    id = taxiRideUiState.tripId,
                    onClick = {
                        listener.onClickActiveTaxiRide(
                            tripId = taxiRideUiState.tripId,
                            isATaxiRide = true
                        )
                    },
                    titleTextColor = if (taxiRideUiState.rideStatus == TripStatus.APPROVED.statusCode) {
                        Theme.colors.primary
                    } else {
                        Theme.colors.contentSecondary
                    }
                ) { textStyle ->
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (taxiRideUiState.rideStatus == TripStatus.APPROVED.statusCode) {
                            Text(text = taxiRideUiState.taxiColor.name, style = textStyle)
                            Circle()
                            Text(text = taxiRideUiState.taxiPlateNumber, style = textStyle)
                            Circle()
                        }
                        Text(
                            text = "${taxiRideUiState.rideEstimatedTime} min to arrive",
                            style = textStyle
                        )
                    }
                }
            }

            // delivery rides
            items(
                items = state.liveOrders.deliveryOrders,
                key = { it.tripId }
            ) { deliveryOrder ->
                InProgressCard(
                    painter = painterResource(Resources.images.orderOnTheWay),
                    titleText = Resources.strings.orderOnTheWay,
                    titleTextColor = Theme.colors.primary,
                    id = deliveryOrder.tripId,
                    onClick = {
                        listener.onClickActiveFoodOrder(
                            orderId = "",
                            tripId = deliveryOrder.tripId,
                            isATaxiRide = false
                        )
                    }
                ) { textStyle ->
                    Text(
                        text = "From ${deliveryOrder.restaurantName}",
                        style = textStyle
                    )
                }
            }

            // food orders
            items(items = state.liveOrders.foodOrders, key = { it.orderId }) { foodOrder ->
                InProgressCard(
                    painter =
                    if (foodOrder.orderStatus == FoodOrder.OrderStatusInRestaurant.APPROVED.statusCode) {
                        painterResource(Resources.images.approvedFood)
                    } else {
                        painterResource(Resources.images.inCookingFood)
                    },
                    titleText = if (foodOrder.orderStatus == FoodOrder.OrderStatusInRestaurant.APPROVED.statusCode) {
                        Resources.strings.orderPlaced
                    } else {
                        Resources.strings.orderInCooking
                    },
                    id = foodOrder.orderId,
                    onClick = {
                        listener.onClickActiveFoodOrder(
                            orderId = foodOrder.orderId,
                            tripId = "",
                            isATaxiRide = false
                        )
                    }
                ) { textStyle ->
                    Text(
                        text = "From ${foodOrder.restaurantName}",
                        style = textStyle
                    )
                }
            }

        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun LazyListScope.search(onClick: () -> Unit) {
        item {
            BpSimpleTextField(
                text = "",
                hint = Resources.strings.searchHint,
                hintColor = Theme.colors.contentSecondary,
                onTrailingIconClick = onClick,
                onValueChange = { },
                onClick = onClick,
                leadingPainter = painterResource(Resources.images.searchOutlined),
                modifier = Modifier.onFocusChanged {
                    if (it.hasFocus) {
                        onClick()
                    }
                }.padding(horizontal = 16.dp)
            )
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun LastOrder(order: OrderUiState, listener: HomeScreenInteractionListener) {
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                Resources.strings.lastOrder,
                style = Theme.typography.titleLarge.copy(color = Theme.colors.contentPrimary)
            )
            Row(modifier = Modifier.fillMaxWidth().height(80.dp).padding(top = 8.dp)) {
                BpImageLoader(
                    modifier = Modifier.fillMaxHeight().width(104.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    imageUrl = order.image
                )
                Column(
                    modifier = Modifier.padding(8.dp).fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        order.restaurantName,
                        style = Theme.typography.title.copy(color = Theme.colors.contentPrimary)
                    )
                    Text(
                        order.date,
                        style = Theme.typography.body.copy(color = Theme.colors.contentSecondary)
                    )
                    Row(
                        modifier = Modifier.clickable { listener.onClickOrderAgain(order.id) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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
    private fun Wallet(value: String, modifier: Modifier = Modifier) {
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

    @Composable
    private fun Cuisines(
        showSeeAllCuisine: Boolean,
        recommendedCuisines: List<CuisineUiState>,
        onClickSeeAllCuisines: () -> Unit,
        onClickCuisineItem: (String) -> Unit,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            SectionHeader(
                onClickViewAll = onClickSeeAllCuisines,
                title = Resources.strings.cuisineSectionTitle,
                showViewAll = showSeeAllCuisine
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                recommendedCuisines.forEach { cuisine ->
                    CuisineCard(
                        cuisine = cuisine,
                        onClickCuisine = onClickCuisineItem
                    )
                }
            }
        }
    }

    @Composable
    private fun InProgressCard(
        painter: Painter,
        titleText: String,
        id: String,
        onClick: (id: String) -> Unit,
        modifier: Modifier = Modifier,
        titleTextColor: Color = Theme.colors.primary,
        titleTextStyle: TextStyle = Theme.typography.title.copy(color = titleTextColor),
        captionText: @Composable (TextStyle) -> Unit,
    ) {
        Row(
            modifier = modifier
                .heightIn(min = 72.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .noRippleEffect { onClick(id) }
                .roundedBorderShape()
                .padding(horizontal = 16.dp, vertical = 4.dp),
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
}