package org.thechance.common.presentation.overview

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.base.BaseScreen
import org.thechance.common.presentation.composables.OverviewDropDown
import org.thechance.common.presentation.main.RestaurantsTab
import org.thechance.common.presentation.main.TaxisTab
import org.thechance.common.presentation.main.UsersTab
import org.thechance.common.presentation.resources.Resources
import org.thechance.common.presentation.util.kms

object OverviewScreen :
    BaseScreen<OverviewScreenModel, OverviewUiEffect, OverviewUiState, OverviewInteractionListener>() {

    override fun onEffect(effect: OverviewUiEffect, navigator: Navigator) {

    }

    @Composable
    override fun OnRender(state: OverviewUiState, listener: OverviewInteractionListener) {

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier.background(Theme.colors.surface).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.kms)
        ) {

            RevenueCard(listener, state, modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(24.kms)
            ) {

                OverviewCard(
                    modifier = Modifier.weight(1f)
                        .background(Theme.colors.surface)
                        .border(1.kms, Theme.colors.divider, RoundedCornerShape(Theme.radius.medium)),
                    title = Resources.Strings.taxiLabel,
                    onLeadingButtonClicked = listener::onViewMoreTaxiClicked,
                    body = {
                        Box(modifier = Modifier.fillMaxWidth().weight(1f).background(Color.LightGray)) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = "This is Taxi chart",
                                color = Theme.colors.contentPrimary,
                            )
                        }
                    }
                )

                OverviewCard(
                    modifier = Modifier.weight(1f)
                        .background(Theme.colors.surface)
                        .border(1.kms, Theme.colors.divider, RoundedCornerShape(Theme.radius.medium)),
                    title = Resources.Strings.restaurantLabel,
                    onLeadingButtonClicked = listener::onViewMoreRestaurantClicked,
                    body = {
                        Box(modifier = Modifier.fillMaxWidth().weight(1f).background(Color.LightGray)) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = "This is Restaurant chart",
                                color = Theme.colors.contentPrimary,
                            )
                        }
                    }
                )

                OverviewCard(
                    modifier = Modifier.weight(1f)
                        .background(Theme.colors.surface)
                        .border(1.kms, Theme.colors.divider, RoundedCornerShape(Theme.radius.medium)),
                    title = Resources.Strings.users,
                    onLeadingButtonClicked = listener::onViewMoreUsersClicked,
                    body = {
                        Column(modifier = Modifier.weight(1f).fillMaxWidth().verticalScroll(scrollState)) {
                            state.users.forEachIndexed { index, user ->
                                Row(
                                    modifier = Modifier.padding(16.kms),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Image(
                                        modifier = Modifier.size(40.kms),
                                        painter = painterResource(user.image),
                                        contentDescription = null
                                    )
                                    Text(
                                        text = user.name,
                                        style = Theme.typography.body,
                                        modifier = Modifier.padding(start = 16.kms).weight(1f),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                    Text(
                                        text = getPermission(user.permission),
                                        style = Theme.typography.body.copy(color = Theme.colors.contentTertiary)
                                    )
                                }
                                if (index != state.users.lastIndex) {
                                    Divider(
                                        modifier = Modifier,
                                        color = Theme.colors.divider
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }


    @Composable
    override fun Content() {
        val screenModel = getScreenModel<OverviewScreenModel>()
        Init(screenModel)
        LaunchNavigationEffect(screenModel)
    }

    @Composable
    private fun LaunchNavigationEffect(screenModel: OverviewScreenModel) {
        val tabNavigator = LocalTabNavigator.current
        LaunchedEffect(Unit) {
            collectNavigationEffect(screenModel, tabNavigator)
        }
    }

    private suspend fun collectNavigationEffect(
        screenModel: OverviewScreenModel,
        tabNavigator: TabNavigator
    ) {
        screenModel.effect.collect {
            it?.let {
                when (it) {
                    OverviewUiEffect.ViewMoreUsers -> {
                        tabNavigator.current = UsersTab
                    }

                    OverviewUiEffect.ViewMoreRestaurant -> {
                        tabNavigator.current = RestaurantsTab
                    }

                    OverviewUiEffect.ViewMoreTaxis -> {
                        tabNavigator.current = TaxisTab
                    }
                }

            }
        }
    }

    @Composable
    private fun OverviewCard(
        title: String,
        onLeadingButtonClicked: () -> Unit,
        body: @Composable () -> Unit,
        paddingValues: PaddingValues = PaddingValues(24.kms),
        modifier: Modifier = Modifier,
    ) {

        Column(
            modifier = modifier.background(Theme.colors.surface, RoundedCornerShape(8.kms)).padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.kms)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = Theme.typography.headline,
                    color = Theme.colors.contentPrimary,
                    modifier = Modifier.weight(2f)
                )

                BpOutlinedButton(
                    shape = RoundedCornerShape(Theme.radius.small),
                    title = Resources.Strings.viewMore,
                    onClick = onLeadingButtonClicked,
                    textStyle = Theme.typography.body,
                    textPadding = PaddingValues(0.dp),
                    modifier = Modifier.height(32.dp).weight(1f),
                )
            }
            body()
        }
    }

    @Composable
    private fun RevenueCard(
        listener: OverviewInteractionListener,
        state: OverviewUiState,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .background(Theme.colors.surface)
                .border(1.kms, Theme.colors.divider, RoundedCornerShape(Theme.radius.medium))
                .padding(24.kms),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.kms)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Resources.Strings.revenueShare,
                    style = Theme.typography.headline,
                    color = Theme.colors.contentPrimary,
                    modifier = Modifier
                )
                OverviewDropDown(
                    listener::onMenuItemDropDownClicked,
                    listener::onDismissDropDownMenu,
                    listener::onMenuItemClicked,
                    items = state.dropdownMenuState.items,
                    selectedIndex = state.dropdownMenuState.selectedIndex,
                    isExpanded = state.dropdownMenuState.isExpanded,
                )
            }
            Box(modifier = Modifier.fillMaxWidth().weight(1f).background(Color.LightGray)) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "This is bar chart",
                    color = Theme.colors.contentPrimary,
                )
            }
        }
    }

    @Composable
    private fun getPermission(permission: PermissionUiState): String {
        return when (permission) {
            PermissionUiState.RESTAURANT -> Resources.Strings.restaurant
            PermissionUiState.DRIVER -> Resources.Strings.deliveryPermission
            PermissionUiState.END_USER -> Resources.Strings.endUserPermission
            PermissionUiState.SUPPORT -> Resources.Strings.supportPermission
            PermissionUiState.DELIVERY -> Resources.Strings.deliveryPermission
            PermissionUiState.ADMIN -> Resources.Strings.adminPermission
        }
    }

}