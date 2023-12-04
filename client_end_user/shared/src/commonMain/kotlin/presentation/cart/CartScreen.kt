package presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpAppBar
import com.beepbeep.designSystem.ui.theme.Theme
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.cart.composable.ItemCart
import presentation.cart.composable.OrderInfoCard
import com.beepbeep.designSystem.ui.composable.BpToastMessage
import presentation.composable.exitinstion.bottomBorder
import resources.Resources
import util.getNavigationBarPadding

class CartScreen :
    BaseScreen<CartScreenModel, CartUiState, CartUiEffect, CartInteractionListener>() {
    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: CartUiState, listener: CartInteractionListener) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Scaffold(
            topBar = {
                BpAppBar(
                    onNavigateUp = { listener.onClickBack() },
                    title = Resources.strings.yourCart,
                    isBackIconVisible = true,
                    painterResource = painterResource(Resources.images.iconBack),
                    modifier = Modifier.bottomBorder(1.dp, Theme.colors.divider)
                )
            },
            bottomBar = {
                OrderInfoCard(
                    onClickOrderNow = { listener.onClickOrderNow() },
                    totalPrice = "${state.currency} ${state.totalPrice}",
                    isLoading = state.isOrderNowLoading,
                    modifier = Modifier.background(Theme.colors.surface)
                        .padding(bottom = getNavigationBarPadding().calculateBottomPadding())
                )
            }
            ) { paddingValues ->

                LazyColumn(
                    modifier = Modifier.fillMaxSize().background(Theme.colors.background),
                    contentPadding = PaddingValues(
                        top = paddingValues.calculateTopPadding()
                                + getNavigationBarPadding().calculateTopPadding()
                                + 16.dp,
                        bottom = paddingValues.calculateTopPadding() + 16.dp,
                        start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                        end = paddingValues.calculateEndPadding(LocalLayoutDirection.current)
                    )
                ) {
                    itemsIndexed(state.meals) { index, item ->
                        val isDividerVisible = index != state.meals.size - 1
                        ItemCart(
                            onClickMinus = { _, _ ->
                                listener.onClickMinus(index, item.count)
                            },
                            onClickPlus = { _, _ ->
                                listener.onClickPlus(index, item.count)
                            },
                            imageUrl = item.image,
                            restaurantName = item.restaurantName,
                            count = item.count,
                            index = index,
                            isDividerVisible = isDividerVisible,
                            mealName = item.name,
                            price = "${item.currency} ${item.price}"
                        )
                    }
                }

            }
            LaunchedEffect(state.showToast) {
                if (state.showToast) {
                    delay(2000)
                    listener.onDismissSnackBar()
                }
            }
            BpToastMessage(
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(getNavigationBarPadding()),
                state = state.showToast,
                message = Resources.strings.restaurantClosed,
                isError = true,
                successIcon = painterResource(Resources.images.unread),
                warningIcon = painterResource(Resources.images.warningIcon)
            )
        }
    }

    override fun onEffect(effect: CartUiEffect, navigator: Navigator) {
        when (effect) {
            CartUiEffect.NavigateUp -> navigator.pop()
        }
    }
}
