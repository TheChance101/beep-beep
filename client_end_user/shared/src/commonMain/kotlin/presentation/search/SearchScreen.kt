package presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpImageLoader
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.theme.Theme
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.auth.login.LoginScreen
import presentation.base.BaseScreen
import presentation.cart.CartScreen
import presentation.composable.BottomSheet
import presentation.composable.MealBottomSheet
import presentation.composable.MealCard
import presentation.composable.ModalBottomSheetState
import presentation.composable.modifier.noRippleEffect
import presentation.composable.modifier.roundedBorderShape
import presentation.resturantDetails.Composable.NeedToLoginSheet
import com.beepbeep.designSystem.ui.composable.BpToastMessage
import presentation.resturantDetails.Composable.WarningCartIsFullDialog
import presentation.resturantDetails.RestaurantScreen
import resources.Resources
import util.getNavigationBarPadding
import util.getStatusBarPadding
import util.root

class SearchScreen :
    BaseScreen<SearchScreenModel, SearchUiState, SearchUiEffect, SearchInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: SearchUiEffect, navigator: Navigator) {
        when (effect) {
            is SearchUiEffect.NavigateToRestaurant -> navigator.root?.push(RestaurantScreen(effect.restaurantId))
            is SearchUiEffect.NavigateToLogin -> navigator.push(LoginScreen())
            is SearchUiEffect.onGoToCart -> navigator.root?.push(CartScreen())
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: SearchUiState, listener: SearchInteractionListener) {
        val sheetState = remember { ModalBottomSheetState() }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            WarningCartIsFullDialog(
                modifier = Modifier.padding(getNavigationBarPadding()),
                text = Resources.strings.addFromDifferentCartMessage,
                onClickClearCart = listener::onClearCart,
                onClickGoToCart = listener::onGoToCart,
                onDismiss = listener::onDismissSheet,
                isVisitable = state.showWarningCartIsFull
            )
            BottomSheet(
                sheetContent = {
                    if (state.showMealSheet)
                        MealBottomSheet(
                            meal = state.selectedMeal,
                            isLoading = state.isAddToCartLoading,
                            onAddToCart = listener::onAddToCart,
                            onDismissSheet = listener::onDismissSheet,
                            onIncreaseQuantity = listener::onIncreaseMealQuantity,
                            onDecreaseQuantity = listener::onDecreaseMealQuantity
                        )
                    if (state.showLoginSheet)
                        NeedToLoginSheet(
                            text = Resources.strings.loginToAddToFavourite,
                            onClick = listener::onLoginClicked,
                        )
                },
                sheetBackgroundColor = Theme.colors.background,
                onBackGroundClicked = listener::onDismissSheet,
                sheetState = sheetState,
                content = { content(state, listener) }
            )

            LaunchedEffect(state.showToast) {
                if (state.showToast) {
                    delay(2000)
                    listener.onDismissSnackBar()
                }
            }
            LaunchedEffect(state.showToastClearCart) {
                if (state.showToastClearCart) {
                    delay(2000)
                    listener.onDismissSnackBar()
                }
            }

            BpToastMessage(
                modifier = Modifier.align(Alignment.BottomCenter),
                state = state.showToast,
                message = if (state.errorAddToCart == null)
                    Resources.strings.mealAddedToYourCart else Resources.strings.mealFailedToAddInCart,
                isError = state.errorAddToCart != null,
                successIcon = painterResource(Resources.images.unread),
                warningIcon = painterResource(Resources.images.warningIcon)
            )
            BpToastMessage(
                modifier = Modifier.align(Alignment.BottomCenter),
                state = state.showToastClearCart,
                message = Resources.strings.youCanAddMeal ,
                isError = false,
                successIcon = painterResource(Resources.images.unread),
            )
        }

        LaunchedEffect(state.showMealSheet, state.showLoginSheet) {
            if (state.showMealSheet || state.showLoginSheet) {
                sheetState.show()
            } else {
                sheetState.dismiss()
            }
        }

    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
    @Composable
    private fun content(
        state: SearchUiState,
        listener: SearchInteractionListener,
        modifier: Modifier = Modifier,
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Theme.colors.background)
                .padding(getStatusBarPadding())
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {

            stickyHeader {
                BpSimpleTextField(
                    state.query,
                    hint = Resources.strings.searchHint,
                    hintColor = Theme.colors.contentSecondary,
                    onValueChange = listener::onSearchInputChanged,
                    onClick = {},
                    leadingPainter = painterResource(Resources.images.searchOutlined),
                    modifier = Modifier.background(Theme.colors.background)
                        .padding(horizontal = 16.dp),
                )
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.restaurants) {
                        Restaurant(
                            restaurant = it,
                            onClick = listener::onRestaurantClicked
                        )
                    }
                }
            }

            items(state.meals) {
                MealCard(
                    meal = it,
                    modifier = Modifier.noRippleEffect { listener.onMealClicked(it) }
                )
            }
        }
    }

    @Composable
    private fun Restaurant(
        restaurant: RestaurantUiState,
        onClick: (String) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier.widthIn(max = 64.dp).noRippleEffect { onClick(restaurant.id) },
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BpImageLoader(
                modifier = Modifier.roundedBorderShape().size(64.dp),
                imageUrl = restaurant.image,
                errorPlaceholderImageUrl = Resources.images.restaurantErrorPlaceholder,
                contentDescription = restaurant.name,
            )

            Text(
                text = restaurant.name,
                style = Theme.typography.body,
                color = Theme.colors.contentPrimary,
                maxLines = 2,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}