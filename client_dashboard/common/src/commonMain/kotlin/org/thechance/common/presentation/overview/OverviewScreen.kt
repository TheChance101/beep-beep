package org.thechance.common.presentation.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.donutChart.DonutChart
import com.aay.compose.donutChart.model.PieChartData
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
                modifier = Modifier
                    .background(Theme.colors.surface)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.kms)
        ) {

            RevenueCard(listener = listener, state = state)
            Row(
                    modifier = Modifier.height(340.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.kms)
            ) {
                OverviewCard(
                        modifier = Modifier.fillMaxHeight().background(Theme.colors.surface)
                            .weight(1f)
                            .border(
                                    1.kms,
                                    Theme.colors.divider,
                                    RoundedCornerShape(Theme.radius.medium)
                            ),
                        title = Resources.Strings.taxiLabel,
                        onLeadingButtonClicked = listener::onViewMoreTaxiClicked,
                        verticalArrangement = Arrangement.spacedBy(40.dp),
                        content = {
                            TaxiChart(tripsRevenueShare = state.tripsRevenueShare)
                        }
                )
                OverviewCard(
                        modifier = Modifier.weight(1f).fillMaxHeight()
                            .background(Theme.colors.surface)
                            .border(
                                    1.kms,
                                    Theme.colors.divider,
                                    RoundedCornerShape(Theme.radius.medium)
                            ),
                        title = Resources.Strings.restaurantLabel,
                        onLeadingButtonClicked = listener::onViewMoreRestaurantClicked,
                        verticalArrangement = Arrangement.spacedBy(40.dp),
                        content = {
                            RestaurantsChart(ordersRevenueShare = state.ordersRevenueShare)
                        }
                )
                OverviewCard(
                        modifier = Modifier.weight(1f)
                            .background(Theme.colors.surface)
                            .border(
                                    1.kms,
                                    Theme.colors.divider,
                                    RoundedCornerShape(Theme.radius.medium)
                            ),
                        title = Resources.Strings.users,
                        onLeadingButtonClicked = listener::onViewMoreUsersClicked,
                        content = {
                            Column(
                                    modifier = Modifier.weight(1f).fillMaxWidth()
                                        .verticalScroll(scrollState)
                            ) {
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
                                                style = Theme.typography.body.copy(color = Theme.colors.contentPrimary),
                                                modifier = Modifier.padding(start = 16.kms)
                                                    .weight(1f),
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
            it.let {
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
        paddingValues: PaddingValues = PaddingValues(start = 24.kms, top = 24.kms, end = 24.kms),
        modifier: Modifier = Modifier,
        verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.kms),
        content: @Composable ColumnScope.() -> Unit,
    ) {

        Column(
                modifier = modifier
                    .background(Theme.colors.surface, RoundedCornerShape(8.kms))
                    .padding(paddingValues)
                    .defaultMinSize(minHeight = 300.dp, minWidth = 300.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = verticalArrangement
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
                        textPadding = PaddingValues(0.kms),
                        modifier = Modifier.weight(1f).height(32.dp),
                )
            }

            content()
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

            val testBarParameters: List<BarParameters> = listOf(
                    BarParameters(
                            dataName = Resources.Strings.revenue,
                            data = state.revenueData,
                            barColor = Theme.colors.primary,
                    ),
                    BarParameters(
                            dataName = Resources.Strings.earnings,
                            data = state.earningData,
                            barColor = Theme.colors.secondary,
                    ),
            )
            Box(modifier = Modifier.fillMaxWidth().height(400.dp)) {
                BarChart(
                        chartParameters = testBarParameters,
                        gridColor = Theme.colors.divider,
                        xAxisData = state.revenueShare,
                        yAxisStyle = Theme.typography.caption.copy(color = Theme.colors.contentPrimary),
                        xAxisStyle = Theme.typography.body.copy(color = Theme.colors.contentPrimary),
                        yAxisRange = 4,
                        animateChart = true,
                        barWidth = 14.kms,
                        barCornerRadius = 4.kms,
                        backgroundLineWidth = 0.5f,
                        spaceBetweenBars = 8.kms,
                        spaceBetweenGroups = 100.kms,
                        descriptionStyle = Theme.typography.caption.copy(color = Theme.colors.contentPrimary),
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

    //endregion
    @Composable
    fun ColumnScope.TaxiChart(
        tripsRevenueShare: TripsRevenueShareUiState
    ) {
        val testPieChartData: List<PieChartData> = listOf(
                PieChartData(
                        partName = Resources.Strings.accepted,
                        data = tripsRevenueShare.acceptedTrips,
                        color = Theme.colors.primary,
                ),
                PieChartData(
                        partName = Resources.Strings.pending,
                        data = tripsRevenueShare.pendingTrips,
                        color = Theme.colors.primary.copy(alpha = 0.5f),
                ),
                PieChartData(
                        partName = Resources.Strings.rejected,
                        data = tripsRevenueShare.rejectedTrips,
                        color = Theme.colors.primary.copy(alpha = 0.3f),
                ),
                PieChartData(
                        partName = Resources.Strings.canceled,
                        data = tripsRevenueShare.canceledTrips,
                        color = Theme.colors.primary.copy(alpha = 0.1f),
                ),
        )
        DonutChart(
                pieChartData = testPieChartData,
                centerTitle = Resources.Strings.trip,
                centerTitleStyle = Theme.typography.body.copy(color = Theme.colors.contentSecondary),
                outerCircularColor = Theme.colors.contentBorder,
                innerCircularColor = Theme.colors.contentBorder,
                ratioLineColor = Theme.colors.contentBorder,
                legendPosition = LegendPosition.BOTTOM,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textRatioStyle = Theme.typography.caption.copy(color = Theme.colors.contentPrimary),
                descriptionStyle = Theme.typography.caption.copy(color = Theme.colors.contentPrimary),
        )
    }

    @Composable
    fun ColumnScope.RestaurantsChart(
        ordersRevenueShare: OrdersRevenueShareUiState
    ) {
        val testPieChartData: List<PieChartData> = listOf(
                PieChartData(
                        partName = Resources.Strings.completed,
                        data = ordersRevenueShare.completedOrders,
                        color = Theme.colors.primary,
                ),
                PieChartData(
                        partName = Resources.Strings.inTheWay,
                        data = ordersRevenueShare.inTheWayOrders,
                        color = Theme.colors.primary.copy(alpha = 0.5f),
                ),
                PieChartData(
                        partName = Resources.Strings.canceled,
                        data = ordersRevenueShare.canceledOrders,
                        color = Theme.colors.primary.copy(alpha = 0.1f),
                ),
        )

        DonutChart(
                pieChartData = testPieChartData,
                centerTitle = Resources.Strings.orders,
                centerTitleStyle = Theme.typography.body.copy(color = Theme.colors.contentSecondary),
                outerCircularColor = Theme.colors.contentBorder,
                innerCircularColor = Theme.colors.contentBorder,
                ratioLineColor = Theme.colors.contentBorder,
                legendPosition = LegendPosition.BOTTOM,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textRatioStyle = Theme.typography.caption.copy(color = Theme.colors.contentPrimary),
                descriptionStyle = Theme.typography.caption.copy(color = Theme.colors.contentPrimary),
        )
    }


}