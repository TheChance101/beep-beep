package org.thechance.common.presentation.restaurant

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.*
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.base.BaseScreen
import org.thechance.common.presentation.composables.*
import org.thechance.common.presentation.composables.modifier.cursorHoverIconHand
import org.thechance.common.presentation.composables.modifier.noRipple
import org.thechance.common.presentation.composables.table.BpPager
import org.thechance.common.presentation.composables.table.BpTable
import org.thechance.common.presentation.composables.table.TotalItemsIndicator
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.util.kms
import java.awt.Dimension
import kotlin.reflect.KFunction1

class RestaurantScreen :
    BaseScreen<RestaurantScreenModel, RestaurantUIEffect, RestaurantUiState, RestaurantInteractionListener>() {

    @Composable
    override fun Content() {
        Init(getScreenModel())
    }

    override fun onEffect(effect: RestaurantUIEffect, navigator: Navigator) {
        when (effect) {
            else -> {}
        }
    }

    @Composable
    override fun OnRender(state: RestaurantUiState, listener: RestaurantInteractionListener) {
        AnimatedVisibility(visible = state.isNewRestaurantInfoDialogVisible) {
            NewRestaurantInfoDialog(
                modifier = Modifier,
                state = state,
                listener = listener,
            )
        }

        RestaurantAddCuisineDialog(
            listener = listener,
            isVisible = state.restaurantAddCuisineDialogUiState.isVisible,
            cuisineName = state.restaurantAddCuisineDialogUiState.cuisineName,
            cuisines = state.restaurantAddCuisineDialogUiState.cuisines,
        )

        Column(
            Modifier.background(Theme.colors.surface).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.kms),
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
        listener: RestaurantInteractionListener,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.kms),
            verticalAlignment = Alignment.Top
        ) {
            BpSimpleTextField(
                modifier = Modifier.widthIn(min = 340.kms, max = 440.kms),
                hint = Resources.Strings.searchForRestaurants,
                onValueChange = listener::onSearchChange,
                text = state.searchQuery,
                keyboardType = KeyboardType.Text,
                trailingPainter = painterResource(Resources.Drawable.search)
            )

            RestaurantFilterRow(state, listener)

            Spacer(modifier = Modifier.weight(1f))

            BpOutlinedButton(
                title = Resources.Strings.export,
                onClick = { /* TODO: Export */ },
                textPadding = PaddingValues(horizontal = 24.kms),
                modifier = Modifier.cursorHoverIconHand()
            )
            BpOutlinedButton(
                title = Resources.Strings.addCuisine,
                onClick = listener::onClickAddCuisine,
                textPadding = PaddingValues(horizontal = 24.kms),
                modifier = Modifier.cursorHoverIconHand()
            )
            BpButton(
                title = Resources.Strings.newRestaurant,
                onClick = listener::onAddNewRestaurantClicked,
                textPadding = PaddingValues(horizontal = 24.kms),
                modifier = Modifier.cursorHoverIconHand()
            )
        }
    }

    @Composable
    private fun ColumnScope.RestaurantTable(
        state: RestaurantUiState,
        listener: RestaurantInteractionListener,
    ) {
        BpTable(
            data = state.restaurants,
            key = { it.id },
            headers = state.tableHeader,
            modifier = Modifier.fillMaxWidth(),
            rowContent = { restaurant ->
                RestaurantRow(
                    onClickEditRestaurant = listener::showEditRestaurantMenu,
                    onEditRestaurantDismiss = listener::hideEditRestaurantMenu,
                    onClickDeleteRestaurantMenuItem = listener::onClickDeleteRestaurantMenuItem,
                    onClickEditRestaurantMenuItem = listener::onClickEditRestaurantMenuItem,
                    position = state.restaurants.indexOf(restaurant) + 1,
                    restaurant = restaurant,
                    editRestaurantMenu = state.editRestaurantMenu
                )
            },
        )
    }

    @Composable
    private fun RestaurantPagingRow(
        state: RestaurantUiState,
        listener: RestaurantInteractionListener,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(color = Theme.colors.surface),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TotalItemsIndicator(
                numberItemInPage = state.numberOfRestaurantsInPage,
                totalItems = state.numberOfRestaurants,
                itemType = Resources.Strings.restaurant,
                onItemPerPageChange = listener::onItemPerPageChange
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
        onEditRestaurantDismiss: () -> Unit,
        onClickEditRestaurantMenuItem: (RestaurantUiState.RestaurantDetailsUiState) -> Unit,
        onClickDeleteRestaurantMenuItem: (id: String) -> Unit,
        position: Int,
        restaurant: RestaurantUiState.RestaurantDetailsUiState,
        editRestaurantMenu: String,
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
            restaurant.phone,
            style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(otherColumnsWeight),
            maxLines = 1,
        )
        RatingBar(
            rating = restaurant.rate,
            selectedIcon = painterResource(Resources.Drawable.starFilled),
            halfSelectedIcon = painterResource(Resources.Drawable.starHalfFilled),
            modifier = Modifier.weight(otherColumnsWeight),
            iconsSize = 16.kms
        )
        PriceBar(
            priceLevel = 1,
            icon = painterResource(Resources.Drawable.dollarSign),
            iconColor = Theme.colors.success,
            modifier = Modifier.weight(otherColumnsWeight),
            iconsSize = 16.kms
        )

        Text(
            text = "${restaurant.openingTime} - ${restaurant.closingTime}",
            style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(otherColumnsWeight),
            maxLines = 1,
        )
        Box(modifier = Modifier.weight(firstAndLastColumnWeight)) {
            Image(
                painter = painterResource(Resources.Drawable.dots),
                contentDescription = null,
                modifier = Modifier.noRipple { onClickEditRestaurant(restaurant.name) },
                colorFilter = ColorFilter.tint(color = Theme.colors.contentPrimary)
            )
            EditRestaurantDropdownMenu(
                restaurant = restaurant,
                onClickEdit = onClickEditRestaurantMenuItem,
                onClickDelete = onClickDeleteRestaurantMenuItem,
                onDismissRequest = onEditRestaurantDismiss,
                editRestaurantMenu = editRestaurantMenu
            )
        }
    }

    @Composable
    fun RestaurantFilterRow(state: RestaurantUiState, listener: RestaurantInteractionListener) {
        Column {
            BpIconButton(
                content = {
                    Text(
                        text = Resources.Strings.filter,
                        style = Theme.typography.titleMedium
                            .copy(color = Theme.colors.contentTertiary),
                    )
                },
                onClick = listener::onClickDropDownMenu,
                painter = painterResource(Resources.Drawable.filter),
                modifier = Modifier.cursorHoverIconHand()
            )
            RestaurantFilterDropdownMenu(
                onClickRating = listener::onClickFilterRatingBar,
                onClickPrice = listener::onClickFilterPriceBar,
                onDismissRequest = listener::onDismissDropDownMenu,
                onClickCancel = listener::onCancelFilterRestaurantsClicked,
                onClickSave = {
                    listener.onSaveFilterRestaurantsClicked(
                        state.restaurantFilterDropdownMenuUiState.filterRating,
                        state.restaurantFilterDropdownMenuUiState.filterPriceLevel.toString()
                    )
                },
                expanded = state.restaurantFilterDropdownMenuUiState.isFilterDropdownMenuExpanded,
                rating = state.restaurantFilterDropdownMenuUiState.filterRating,
                priceLevel = state.restaurantFilterDropdownMenuUiState.filterPriceLevel,
                onFilterClearAllClicked = listener::onFilterClearAllClicked,
            )
        }
    }

    @Composable
    private fun RestaurantFilterDropdownMenu(
        onClickRating: (Double) -> Unit,
        onClickPrice: KFunction1<Int, Unit>,
        onDismissRequest: () -> Unit,
        onClickCancel: () -> Unit,
        onClickSave: () -> Unit,
        expanded: Boolean,
        rating: Double,
        priceLevel: Int,
        onFilterClearAllClicked: () -> Unit,
    ) {
        BpDropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            offset = DpOffset.Zero.copy(y = 16.kms),
            shape = RoundedCornerShape(Theme.radius.medium),
        ) {
            FilterBox(
                title = Resources.Strings.filter,
                onSaveClicked = onClickSave,
                onCancelClicked = onClickCancel,
                onClearAllClicked = onFilterClearAllClicked,
            ) {
                Column {
                    Text(
                        text = Resources.Strings.rating,
                        style = Theme.typography.title,
                        color = Theme.colors.contentPrimary,
                        modifier = Modifier.padding(start = 24.kms, top = 16.kms)
                    )
                    EditableRatingBar(
                        rating = rating,
                        count = 5,
                        selectedIcon = painterResource(Resources.Drawable.starFilled),
                        halfSelectedIcon = painterResource(Resources.Drawable.starHalfFilled),
                        notSelectedIcon = painterResource(Resources.Drawable.starOutlined),
                        iconsSize = 24.kms,
                        iconsPadding = PaddingValues(horizontal = 8.kms),
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 16.kms)
                            .background(color = Theme.colors.background)
                            .padding(horizontal = 24.kms, vertical = 16.kms),
                        onClick = { onClickRating(it) }
                    )
                    Text(
                        text = Resources.Strings.priceLevel,
                        style = Theme.typography.title,
                        color = Theme.colors.contentPrimary,
                        modifier = Modifier.padding(start = 24.kms, top = 32.kms)
                    )
                    EditablePriceBar(
                        priceLevel = priceLevel,
                        count = 3,
                        icon = painterResource(Resources.Drawable.dollarSign),
                        enabledIconsColor = Theme.colors.success,
                        disabledIconsColor = Theme.colors.disable,
                        iconsPadding = PaddingValues(horizontal = 8.kms),
                        iconsSize = 16.kms,
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 16.kms)
                            .background(color = Theme.colors.background)
                            .padding(horizontal = 24.kms, vertical = 16.kms),
                        onClick = { onClickPrice(it) }
                    )
                }
            }
        }
    }

    @Composable
    private fun RestaurantAddCuisineDialog(
        listener: AddCuisineInteractionListener,
        isVisible: Boolean,
        cuisineName: String,
        cuisines: List<String>,
    ) {
        Dialog(
            visible = isVisible,
            transparent = true,
            undecorated = true,
            resizable = false,
            onCloseRequest = listener::onCloseAddCuisineDialog,
        ) {
            window.minimumSize = Dimension(400, 405)
            Column(
                modifier = Modifier
                    .padding(top = 16.kms, start = 16.kms, end = 16.kms)
                    .shadow(elevation = 5.kms)
                    .background(Theme.colors.surface, RoundedCornerShape(8.kms))
            ) {
                Text(
                    text = Resources.Strings.cuisines,
                    style = Theme.typography.headline,
                    color = Theme.colors.contentPrimary,
                    modifier = Modifier.padding(top = 24.kms, start = 24.kms)
                )
                BpSimpleTextField(
                    text = cuisineName,
                    hint = Resources.Strings.cuisines,
                    onValueChange = listener::onChangeCuisineName,
                    modifier = Modifier.padding(top = 24.kms, start = 24.kms, end = 24.kms)
                )
                LazyColumn(
                    modifier = Modifier.padding(top = 16.kms).background(Theme.colors.background)
                        .fillMaxWidth().heightIn(min = 64.kms, max = 256.kms)
                ) {
                    items(cuisines) { cuisineName ->
                        Row(
                            modifier = Modifier.padding(horizontal = 24.kms, vertical = 16.kms),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                cuisineName,
                                style = Theme.typography.caption,
                                color = Theme.colors.contentPrimary,
                            )
                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                painter = painterResource(Resources.Drawable.trashBin),
                                contentDescription = null,
                                tint = Theme.colors.primary,
                                modifier = Modifier
                                    .noRipple { listener.onClickDeleteCuisine(cuisineName) }
                            )
                        }
                    }
                }
                Row(
                    Modifier.fillMaxWidth().padding(24.kms),
                    horizontalArrangement = Arrangement.Center
                ) {

                    BpTransparentButton(
                        title = Resources.Strings.cancel,
                        onClick = listener::onCloseAddCuisineDialog,
                        modifier = Modifier.padding(end = 16.kms)
                            .height(32.dp)
                            .weight(1f)
                    )
                    BpOutlinedButton(
                        title = Resources.Strings.add,
                        onClick = listener::onClickCreateCuisine,
                        modifier = Modifier.height(32.dp).weight(3f),
                        textPadding = PaddingValues(0.dp)
                    )
                }
            }
        }
    }

    @Composable
    private fun EditRestaurantDropdownMenu(
        restaurant: RestaurantUiState.RestaurantDetailsUiState,
        onClickEdit: (RestaurantUiState.RestaurantDetailsUiState) -> Unit,
        onClickDelete: (id: String) -> Unit,
        onDismissRequest: () -> Unit,
        editRestaurantMenu: String,
    ) {
        BpDropdownMenu(
            expanded = restaurant.name == editRestaurantMenu,
            onDismissRequest = onDismissRequest,
            shape = RoundedCornerShape(Theme.radius.medium).copy(topEnd = CornerSize(0.dp)),
            offset = DpOffset.Zero.copy(x = (-178).kms)
        ) {
            Column {
                BpDropdownMenuItem(
                    onClick = {
                        onClickEdit(restaurant)
                    },
                    text = Resources.Strings.edit,
                    leadingIconPath = Resources.Drawable.permission,
                    isSecondary = false,
                    showBottomDivider = true
                )
                BpDropdownMenuItem(
                    onClick = {
                        onClickDelete(restaurant.id)
                    },
                    text = Resources.Strings.delete,
                    leadingIconPath = Resources.Drawable.delete,
                    isSecondary = true,
                    showBottomDivider = false
                )
            }
        }
    }
}