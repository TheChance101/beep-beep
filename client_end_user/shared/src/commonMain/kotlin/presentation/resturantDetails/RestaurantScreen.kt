package presentation.resturantDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import domain.entity.PriceLevel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.auth.login.LoginScreen
import presentation.base.BaseScreen
import presentation.composable.BottomSheet
import presentation.composable.BpImageCard
import presentation.composable.BpPriceLevel
import presentation.composable.RatingBar
import presentation.composable.SectionHeader
import presentation.composable.modifier.noRippleEffect
import presentation.resturantDetails.Composable.Chip
import presentation.resturantDetails.Composable.MealBottomSheet
import presentation.resturantDetails.Composable.NeedToLoginSheet
import resources.Resources

object RestaurantScreen :
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
        initScreen(getScreenModel())
    }

    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun onRender(state: RestaurantUIState, listener: RestaurantInteractionListener) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                BottomSheet(
                    sheetContent = {
                        if(state.showMealSheet) {
                            MealBottomSheet(
                                meal = state.meal,
                                onClick = {

                                },
                                listener = listener
                            )
                        }
                        if(state.showLoginSheet && !state.isLogin) {
                            NeedToLoginSheet(
                                onClick = {
                                    listener.onDismissSheet()
                                    listener.onGoToLogin()
                                }
                            )
                        }
                    },
                    sheetBackgroundColor = Theme.colors.background,
                    onBackGroundClicked = listener::onDismissSheet,
                    sheetState = state.sheetState,
                ) {
                Image(
                    painter = painterResource(Resources.images.placeholder),
                    contentDescription = "background",

                    )
                Row(
                    modifier = Modifier.padding(16.dp).height(56.dp).fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        Modifier.size(40.dp).clip(RoundedCornerShape(Theme.radius.medium))
                            .background(color = Theme.colors.surface)
                            .noRippleEffect { listener.onBack() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Resources.images.iconBack),
                            contentDescription = null,
                            tint = Theme.colors.contentPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(.75f)
                        .verticalScroll(rememberScrollState())
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                        .background(Theme.colors.surface).align(Alignment.BottomCenter)

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
                        Image(
                            painter = painterResource(if (state.isFavourite) Resources.images.heartFilled else Resources.images.heart),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                                .noRippleEffect {
                                    if (state.isLogin) {
                                        listener.onAddToFavourite()
                                    } else {
                                        listener.onShowSheet()
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
                        horizontalArrangement = Arrangement.spacedBy(4.dp),

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
                        Chip(color = Theme.colors.secondary) {
                            Text(
                                text = "${state.restaurantInfo.discount}% ${Resources.strings.off}",
                                style = Theme.typography.body,
                                color = Theme.colors.primary,
                            )
                        }
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
                    Divider(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        color = Theme.colors.contentBorder
                    )

                    SectionHeader(
                        Resources.strings.mostOrdered,
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(state.mostOrders.size) { index ->
                            BpImageCard(
                                title = state.mostOrders[index].name,
                                painter = painterResource(Resources.images.placeholder),
                                priceLevel = PriceLevel.LOW,
                                hasPriceLevel = false,
                                hasPrice = true,
                                hasRate = false,
                                price = state.mostOrders[index].price,
                                currency = state.mostOrders[index].currency,
                                modifier = Modifier.noRippleEffect {
                                 listener.onGoToDetails(state.mostOrders[index].id)
                                }
                            )
                        }
                    }
                    SectionHeader(
                        Resources.strings.sweets,
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, top = 28.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        items(state.sweets.size) { index ->
                            BpImageCard(
                                title = state.sweets[index].name,
                                painter = painterResource(Resources.images.placeholder),
                                priceLevel = PriceLevel.LOW,
                                hasPriceLevel = false,
                                hasPrice = true,
                                price = state.mostOrders[index].price,
                                currency = state.sweets[index].currency,
                                hasRate = false,
                            )
                        }
                    }
                }
            }
        }
    }
}



