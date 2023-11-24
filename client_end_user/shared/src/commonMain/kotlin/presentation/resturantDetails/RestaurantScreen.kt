package presentation.resturantDetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf
import presentation.auth.login.LoginScreen
import presentation.base.BaseScreen
import presentation.composable.BackButton
import presentation.composable.BottomSheet
import presentation.composable.BpImageLoader
import presentation.composable.BpPriceLevel
import presentation.composable.ItemSection
import presentation.composable.MealBottomSheet
import presentation.composable.RatingBar
import presentation.composable.modifier.noRippleEffect
import presentation.resturantDetails.Composable.Chip
import presentation.resturantDetails.Composable.NeedToLoginSheet
import presentation.resturantDetails.Composable.ToastMessage
import resources.Resources
import util.getNavigationBarPadding

data class RestaurantScreen(val restaurantId: String) :
    BaseScreen<RestaurantScreenModel, RestaurantUIState, RestaurantUIEffect, RestaurantInteractionListener>() {

    override fun onEffect(effect: RestaurantUIEffect, navigator: Navigator) {
        when (effect) {
            is RestaurantUIEffect.onBack -> navigator.pop()
            is RestaurantUIEffect.onGoToDetails -> {}
            is RestaurantUIEffect.onGoToLogin -> navigator.push(LoginScreen())
        }
    }

    @Composable
    override fun Content() {
        initScreen(getScreenModel(parameters = { parametersOf(restaurantId) }))
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: RestaurantUIState, listener: RestaurantInteractionListener) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {

            val favoriteColor by animateColorAsState(
                if (state.isFavourite) {
                    Theme.colors.primary
                } else {
                    Theme.colors.contentTertiary
                }
            )

            BottomSheet(
                sheetContent = {
                    if (state.showMealSheet)
                        MealBottomSheet(
                            modifier = Modifier.padding(getNavigationBarPadding()),
                            meal = state.selectedMeal,
                            isLoading = state.isAddToCartLoading,
                            onAddToCart = listener::onAddToCart,
                            onDismissSheet = listener::onDismissSheet,
                            onIncreaseQuantity = listener::onIncreaseMealQuantity,
                            onDecreaseQuantity = listener::onDecreaseMealQuantity
                        )
                    if (state.showLoginSheet)
                        NeedToLoginSheet(
                            modifier = Modifier.padding(getNavigationBarPadding()),
                            text = Resources.strings.loginToAddToFavourite,
                            onClick = {
                                listener.onDismissSheet()
                                listener.onGoToLogin()
                            }
                        )
                },
                sheetBackgroundColor = Theme.colors.background,
                onBackGroundClicked = listener::onDismissSheet,
                sheetState = state.sheetState,
            ) {

                BpImageLoader(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.30f)
                        .align(Alignment.TopCenter),
                    contentScale = ContentScale.Crop,
                    imageUrl = state.restaurantInfo.image,
                    contentDescription = state.restaurantInfo.name
                )

                BackButton(
                    onClick = { listener.onBack() },
                    modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp),
                    icon = Resources.images.iconBack
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.75f)
                        .verticalScroll(rememberScrollState())
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                        .background(Theme.colors.surface).align(Alignment.BottomCenter)
                        .padding(getNavigationBarPadding())
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = state.restaurantInfo.name,
                            style = Theme.typography.headline,
                            color = Theme.colors.contentPrimary
                        )

                        Icon(
                            painter = painterResource(if (state.isFavourite) Resources.images.heartFilled else Resources.images.heart),
                            tint = favoriteColor,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                                .noRippleEffect {
                                    if (state.isLogin) {
                                        listener.onAddToFavourite()
                                    } else {
                                        listener.onShowLoginSheet()
                                    }
                                }

                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(Resources.images.mapPoint),
                            contentDescription = null,
                            tint = Theme.colors.contentTertiary,
                        )
                        Text(
                            text = state.restaurantInfo.address,
                            style = Theme.typography.caption,
                            color = Theme.colors.contentTertiary
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        RatingBar(currentRating = state.restaurantInfo.rating)
                        BpPriceLevel(state.restaurantInfo.priceLevel)
                    }
                    Text(
                        text = state.restaurantInfo.description,
                        style = Theme.typography.body,
                        color = Theme.colors.contentSecondary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(16.dp)
                    )
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        AnimatedVisibility(state.restaurantInfo.discount > 0) {
                            Chip(color = Theme.colors.secondary) {
                                Text(
                                    text = "${state.restaurantInfo.discount}% ${Resources.strings.off}",
                                    style = Theme.typography.body,
                                    color = Theme.colors.primary,
                                )
                            }
                        }
                        AnimatedVisibility(state.restaurantInfo.hasFreeDelivery) {
                            Chip(color = Theme.colors.successContainer) {
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(
                                        painter = painterResource(Resources.images.scooter),
                                        contentDescription = null,
                                        tint = Theme.colors.success,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = Resources.strings.free,
                                        style = Theme.typography.body,
                                        color = Theme.colors.success,
                                    )
                                }
                            }
                        }
                    }

                    Divider(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        color = Theme.colors.contentBorder
                    )

                    state.cuisines.forEach { cuisine ->
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            ItemSection(
                                onClickItem = listener::onGoToDetails,
                                header = cuisine.name,
                                ids = cuisine.meals.map { it.id },
                                titles = cuisine.meals.map { it.name },
                                hasPrice = true,
                                prices = cuisine.meals.map { it.price },
                                imageUrls = cuisine.meals.map { it.image },
                                modifier = Modifier.padding(getNavigationBarPadding())
                            )
                        }
                    }
                }
            }

            ToastMessage(
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(getNavigationBarPadding()),
                state = state.showToast,
                message = if (state.errorAddToCart == null) {
                    Resources.strings.mealAddedToYourCart
                } else {
                    Resources.strings.mealFailedToAddInCart
                },
            )
        }
    }
}




