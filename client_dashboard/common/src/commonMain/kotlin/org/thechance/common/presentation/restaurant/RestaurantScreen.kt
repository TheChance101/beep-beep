package org.thechance.common.presentation.restaurant

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpIconButton
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.LocalDimensions
import org.thechance.common.presentation.base.BaseScreen
import org.thechance.common.presentation.composables.BpDropdownMenu
import org.thechance.common.presentation.composables.EditablePriceBar
import org.thechance.common.presentation.composables.EditableRatingBar
import org.thechance.common.presentation.composables.PriceBar
import org.thechance.common.presentation.composables.RatingBar
import org.thechance.common.presentation.composables.modifier.cursorHoverIconHand
import org.thechance.common.presentation.composables.modifier.noRipple
import org.thechance.common.presentation.composables.table.BpPager
import org.thechance.common.presentation.composables.table.BpTable
import org.thechance.common.presentation.composables.table.TotalItemsIndicator

class RestaurantScreen :
    BaseScreen<RestaurantScreenModel, RestaurantUIEffect, RestaurantUiState, RestaurantInteractionListener>() {

    @Composable
    override fun Content() {
        Init(getScreenModel())
    }

    override fun onEffect(effect: RestaurantUIEffect, navigator: Navigator) {
        when (effect) {
            //TODO: effects
            else -> {}
        }
    }


    @Composable
    override fun OnRender(state: RestaurantUiState, listener: RestaurantInteractionListener) {
        Column(
            Modifier.background(Theme.colors.surface).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LocalDimensions.current.space16),
        ) {
            RestaurantScreenTopRow(state = state, listener = listener)

            RestaurantTable(state = state, listener = listener)

            RestaurantPagingRow(state = state, listener = listener)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun RestaurantScreenTopRow(
        state: RestaurantUiState,
        listener: RestaurantInteractionListener
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(LocalDimensions.current.space16),
            verticalAlignment = Alignment.Top
        ) {
            BpSimpleTextField(
                modifier = Modifier.widthIn(min = 340.dp, max = 440.dp),
                hint = "Search for restaurants",
                onValueChange = listener::onSearchChange,
                text = state.search,
                keyboardType = KeyboardType.Text,
                trailingPainter = painterResource("ic_search.svg")
            )
            Column {
                BpIconButton(
                    content = {
                        Text(
                            "Filter",
                            style = Theme.typography.titleMedium
                                .copy(color = Theme.colors.contentTertiary),
                        )
                    },
                    onClick = listener::onClickDropDownMenu,
                    painter = painterResource("ic_filter.svg"),
                    modifier = Modifier.cursorHoverIconHand()
                )
                RestaurantFilterDropdownMenu(
                    onClickRating = listener::onClickFilterRatingBar,
                    onClickPrice = listener::onClickFilterPriceBar,
                    onDismissRequest = listener::onDismissDropDownMenu,
                    onClickCancel = listener::onCancelFilterRestaurantsClicked,
                    onClickSave = {
                        listener.onSaveFilterRestaurantsClicked(
                            state.filterRating,
                            state.filterPriceLevel
                        )
                    },
                    expanded = state.isFilterDropdownMenuExpanded,
                    rating = state.filterRating,
                    priceLevel = state.filterPriceLevel,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            BpOutlinedButton(
                title = "Export",
                onClick = { /* TODO: Export */ },
                textPadding = PaddingValues(horizontal = LocalDimensions.current.space24),
                modifier = Modifier.cursorHoverIconHand()
            )
            BpOutlinedButton(
                title = "Add cuisine",
                onClick = { /* TODO: Show Add cuisine DropdownMenu */ },
                textPadding = PaddingValues(horizontal = LocalDimensions.current.space24),
                modifier = Modifier.cursorHoverIconHand()
            )
            BpButton(
                title = "New Restaurant",
                onClick = { /*TODO:  Show New Restaurant Dialog */ },
                textPadding = PaddingValues(horizontal = LocalDimensions.current.space24),
                modifier = Modifier.cursorHoverIconHand()
            )
        }
    }

    @Composable
    private fun RestaurantTable(
        state: RestaurantUiState,
        listener: RestaurantInteractionListener
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            BpTable(
                data = state.restaurants,
                key = { it.name },
                headers = state.tableHeader,
                modifier = Modifier.fillMaxWidth(),
                rowContent = { restaurant ->
                    RestaurantRow(
                        onClickEditRestaurant = { /* TODO: Show Edit Restaurant DropdownMenu */ },
                        position = state.restaurants.indexOf(restaurant) + 1,
                        restaurant = restaurant,
                    )
                },
            )
        }
    }

    @Composable
    private fun RestaurantPagingRow(
        state: RestaurantUiState,
        listener: RestaurantInteractionListener
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TotalItemsIndicator(
                numberItemInPage = state.numberOfItemsInPage.toString(),
                totalItems = state.numberOfRestaurants,
                itemType = "restaurant",
                onItemPerPageChange = { listener.onItemPerPageChange(it.toInt()) }
            )
            BpPager(
                maxPages = state.maxPageCount,
                currentPage = state.selectedPageNumber,
                onPageClicked = listener::onPageClicked,
            )
        }
    }

    @Composable
    private fun RowScope.RestaurantRow(
        onClickEditRestaurant: (restaurantName: String) -> Unit,
        position: Int,
        restaurant: RestaurantUiState.RestaurantDetailsUiState,
        firstAndLastColumnWeight: Float = 1f,
        otherColumnsWeight: Float = 3f,
    ) {
        Text(
            position.toString(),
            style = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
            modifier = Modifier.weight(firstAndLastColumnWeight),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            restaurant.name,
            style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(otherColumnsWeight),
            maxLines = 1,
        )

        Text(
            restaurant.ownerUsername,
            style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(otherColumnsWeight),
            maxLines = 1,
        )
        Text(
            restaurant.phoneNumber,
            style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(otherColumnsWeight),
            maxLines = 1,
        )
        RatingBar(
            rating = restaurant.rating,
            selectedIcon = painterResource(if (isSystemInDarkTheme()) "ic_filled_star_dark.svg" else "ic_filled_star_light.svg"),
            halfSelectedIcon = painterResource(if (isSystemInDarkTheme()) "ic_half_filled_star_dark.svg" else "ic_half_filled_star_light.svg"),
            modifier = Modifier.weight(otherColumnsWeight),
            iconsSize = LocalDimensions.current.space16
        )
        PriceBar(
            priceLevel = restaurant.priceLevel,
            icon = painterResource("ic_dollar_sign.svg"),
            iconColor = Theme.colors.success,
            modifier = Modifier.weight(otherColumnsWeight),
            iconsSize = LocalDimensions.current.space16
        )

        Text(
            restaurant.workingHours,
            style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(otherColumnsWeight),
            maxLines = 1,
        )

        Image(
            painter = painterResource("horizontal_dots.xml"),
            contentDescription = null,
            modifier = Modifier.noRipple { onClickEditRestaurant(restaurant.name) }
                .weight(firstAndLastColumnWeight),
            colorFilter = ColorFilter.tint(color = Theme.colors.contentPrimary)
        )
    }


    @Composable
    private fun RestaurantFilterDropdownMenu(
        onClickRating: (Double) -> Unit,
        onClickPrice: (Int) -> Unit,
        onDismissRequest: () -> Unit,
        onClickCancel: () -> Unit,
        onClickSave: () -> Unit,
        expanded: Boolean,
        rating: Double,
        priceLevel: Int
    ) {
        BpDropdownMenu(
            onDismissRequest = onDismissRequest,
            expanded = expanded,
            shape = RoundedCornerShape(LocalDimensions.current.space8),
        ) {
            Column(
                modifier = Modifier.background(
                    color = Theme.colors.surface,
                    shape = RoundedCornerShape(LocalDimensions.current.space8)
                )
            ) {
                Text(
                    text = "Filter",
                    style = Theme.typography.headline,
                    color = Theme.colors.contentPrimary,
                    modifier = Modifier.padding(
                        start = LocalDimensions.current.space24,
                        top = LocalDimensions.current.space24
                    )
                )
                Text(
                    text = "Rating",
                    style = Theme.typography.title,
                    color = Theme.colors.contentPrimary,
                    modifier = Modifier.padding(
                        start = LocalDimensions.current.space24,
                        top = LocalDimensions.current.space40
                    )
                )
                EditableRatingBar(
                    rating = rating,
                    count = 5,
                    selectedIcon = painterResource(
                        if (isSystemInDarkTheme()) "ic_filled_star_dark.svg" else "ic_filled_star_light.svg"
                    ),
                    halfSelectedIcon = painterResource(
                        if (isSystemInDarkTheme()) "ic_half_filled_star_dark.svg" else "ic_half_filled_star_light.svg"
                    ),
                    notSelectedIcon = painterResource(
                        if (isSystemInDarkTheme()) "ic_star_dark.svg" else "ic_star_light.svg"
                    ),
                    iconsSize = LocalDimensions.current.space24,
                    iconsPadding = PaddingValues(horizontal = LocalDimensions.current.space8),
                    modifier = Modifier.fillMaxWidth().padding(top = LocalDimensions.current.space16)
                        .background(color = Theme.colors.background)
                        .padding(
                            horizontal = LocalDimensions.current.space24,
                            vertical = LocalDimensions.current.space16
                        ),
                    onClick = { onClickRating(it) }
                )

                Text(
                    text = "Price level",
                    style = Theme.typography.title,
                    color = Theme.colors.contentPrimary,
                    modifier = Modifier.padding(
                        start = LocalDimensions.current.space24,
                        top = LocalDimensions.current.space32
                    )
                )
                EditablePriceBar(
                    priceLevel = priceLevel,
                    count = 3,
                    icon = painterResource("ic_dollar_sign.svg"),
                    enabledIconsColor = Theme.colors.success,
                    disabledIconsColor = Theme.colors.disable,
                    iconsPadding = PaddingValues(horizontal = LocalDimensions.current.space8),
                    iconsSize = LocalDimensions.current.space16,
                    modifier = Modifier.fillMaxWidth().padding(top = LocalDimensions.current.space16)
                        .background(color = Theme.colors.background)
                        .padding(
                            horizontal = LocalDimensions.current.space24,
                            vertical = LocalDimensions.current.space16
                        ),
                    onClick = { onClickPrice(it) }
                )

                Row(
                    Modifier.fillMaxWidth().padding(LocalDimensions.current.space24),
                    horizontalArrangement = Arrangement.Center
                ) {
                    BpTransparentButton(
                        title = "Cancel",
                        onClick = { onClickCancel(); onDismissRequest() },
                        modifier = Modifier.padding(end = LocalDimensions.current.space16)
                            .height(LocalDimensions.current.space32)
                            .weight(1f)
                    )
                    BpOutlinedButton(
                        title = "Save",
                        onClick = { onClickSave(); onDismissRequest() },
                        modifier = Modifier.height(LocalDimensions.current.space32).weight(3f),
                        textPadding = PaddingValues(0.dp)
                    )
                }
            }
        }
    }
}
