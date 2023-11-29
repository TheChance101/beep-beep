package presentation.restaurantSelection

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpThreeDotLoadingIndicator
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.base.BaseScreen
import presentation.composable.RestaurantInformation
import presentation.main.MainScreen
import resources.Resources
import util.getNavigationBarPadding
import util.getStatusBarPadding

class RestaurantSelectionScreen :
    BaseScreen<RestaurantSelectionScreenModel, RestaurantScreenUIState, RestaurantSelectionScreenUIEffect, RestaurantSelectionScreenInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    @Composable
    override fun onRender(
        state: RestaurantScreenUIState,
        listener: RestaurantSelectionScreenInteractionListener,
    ) {
        val lazyListState = rememberLazyListState()
        var isExpanding by remember { mutableStateOf(false) }
        val bottomSheetSize by animateFloatAsState(
            if (isExpanding) FULL_SCREEN else SEVENTY_PERCENT_SCREEN, tween(200)
        )
        val roundedCornerShape by animateDpAsState(if (isExpanding) 0.dp else 8.dp)

        LaunchedEffect(lazyListState.isScrollInProgress) {
            isExpanding = lazyListState.canScrollBackward
        }

        Box(
            modifier = Modifier.fillMaxSize()
                .background(Theme.colors.primary)
        ) {

            AppLogoHeader()

            AnimatedContent(
                state, modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight(bottomSheetSize)
                    .clip(
                        RoundedCornerShape(
                            topStart = roundedCornerShape,
                            topEnd = roundedCornerShape
                        )
                    )
                    .background(Theme.colors.surface)
                    .align(Alignment.BottomCenter)
            ) {
                if (state.isLoading) {
                    Loading(bottomSheetSize)

                } else {
                    BottomSheetContent(lazyListState, state, listener)
                }
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun AppLogoHeader() {
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(THIRTY_PERCENT_SCREEN)
        ) {

            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Resources.images.pattern),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Icon(
                modifier = Modifier.height(66.dp).width(145.dp)
                    .align(Alignment.Center),
                tint = Color.White,
                painter = painterResource(Resources.images.bpLogo),
                contentDescription = null
            )
        }
    }

    @Composable
    private fun Loading(bottomSheetSize: Float) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(bottomSheetSize)
        ) {
            BpThreeDotLoadingIndicator(
                dotColor = Theme.colors.primary,
                modifier = Modifier.zIndex(5f)
                    .align(Alignment.Center)
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun BottomSheetContent(
        lazyListState: LazyListState,
        state: RestaurantScreenUIState,
        listener: RestaurantSelectionScreenInteractionListener,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = getNavigationBarPadding().calculateBottomPadding()),
            state = lazyListState,
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        ) {
            stickyHeader {
                HeaderTitles()
            }
            itemsIndexed(state.restaurants) { index, item ->
                RestaurantSelectionItem(item, listener)
                AnimatedVisibility(index != state.restaurants.size - 1) {
                    Divider(Modifier.background(Theme.colors.divider))
                }
            }
        }
    }


    override fun onEffect(effect: RestaurantSelectionScreenUIEffect, navigator: Navigator) {
        when (effect) {
            is RestaurantSelectionScreenUIEffect.NavigateToMainScreen -> navigator.replaceAll(
                MainScreen()
            )
        }
    }

    @Composable
    private fun RestaurantSelectionItem(
        item: RestaurantUIState,
        listener: RestaurantSelectionScreenInteractionListener,
    ) {
        RestaurantInformation(
            onRestaurantClick = {
                listener.onClickRestaurant(
                    item.restaurantId,
                    item.location,
                    item.address
                )
            },
            restaurantName = item.restaurantName,
            restaurantNumber = item.restaurantPhoneNumber,
            isOpen = item.isOpen
        )
    }


    @Composable
    private fun HeaderTitles() {
        Column(
            modifier = Modifier.fillMaxSize().background(Theme.colors.surface)
                .padding(getStatusBarPadding()).padding(top = 8.dp)
        ) {
            Text(
                text = Resources.strings.chooseYourRestaurant,
                style = Theme.typography.headline.copy(color = Theme.colors.contentPrimary)
            )

            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                text = Resources.strings.pickWhichRestaurant,
                style = Theme.typography.body.copy(color = Theme.colors.contentTertiary)
            )
        }
    }

    private companion object {
        const val FULL_SCREEN = 1f
        const val THIRTY_PERCENT_SCREEN = 0.35f
        const val SEVENTY_PERCENT_SCREEN = 0.65f
    }
}

