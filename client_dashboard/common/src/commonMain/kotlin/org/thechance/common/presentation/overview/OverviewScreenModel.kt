package org.thechance.common.presentation.overview

import org.thechance.common.domain.entity.RevenueShare
import org.thechance.common.domain.entity.TotalRevenueShare
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.usecase.IExploreDashboardUseCase
import org.thechance.common.domain.util.RevenueShareDate
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState

class OverviewScreenModel(
    private val exploreDashboard: IExploreDashboardUseCase
) : BaseScreenModel<OverviewUiState, OverviewUiEffect>(OverviewUiState()),
    OverviewInteractionListener {

    init {
        initOverviewScreenData()
    }

    override fun onRetry() {
        initOverviewScreenData()
    }

    private fun initOverviewScreenData() {
        getLatestRegisteredUsers()
        getRevenueShare(RevenueShareDate.MONTHLY)
        getDashboardRevenueShare()
    }

    private fun getRevenueShare(revenueShareDate: RevenueShareDate) {
        tryToExecute(
            { exploreDashboard.getRevenueShare(revenueShareDate) },
            ::onGetRevenueShareSuccessfully,
            ::onError
        )
    }

    private fun getDashboardRevenueShare() {
        tryToExecute(
            { exploreDashboard.getDashboardRevenueShare() },
            ::onGetDashboardRevenueSuccessfully,
            ::onError
        )
    }

    private fun onGetDashboardRevenueSuccessfully(revenueShare: RevenueShare) {
        updateState {
            it.copy(
                tripsRevenueShare = revenueShare.tripsRevenueShare.toUiState(),
                ordersRevenueShare = revenueShare.ordersRevenueShare.toUiState()
            )
        }
    }

    private fun onGetRevenueShareSuccessfully(revenueShare: TotalRevenueShare) {
        updateState {
            it.copy(
                revenueData = revenueShare.revenueData,
                earningData = revenueShare.earningData,
                revenueShare = revenueShare.revenueShare
            )
        }
    }


    override fun onMenuItemDropDownClicked() {
        updateState { state ->
            state.copy(
                dropdownMenuState = state.dropdownMenuState.copy(
                    isExpanded = !state.dropdownMenuState.isExpanded
                )
            )
        }
    }

    override fun onMenuItemClicked(index: Int) {
        updateState { state ->
            state.copy(
                dropdownMenuState = state.dropdownMenuState.copy(
                    isExpanded = false, selectedIndex = index,
                )
            )
        }
        when (index) {
            RevenueShareDate.MONTHLY.value -> getRevenueShare(RevenueShareDate.MONTHLY)
            RevenueShareDate.WEEKLY.value-> getRevenueShare(RevenueShareDate.WEEKLY)
            RevenueShareDate.DAILY.value -> getRevenueShare(RevenueShareDate.DAILY)
        }
    }

    override fun onDismissDropDownMenu() {
        updateState { state ->
            state.copy(
                dropdownMenuState = state.dropdownMenuState.copy(
                    isExpanded = false
                )
            )
        }
    }

    override fun onViewMoreUsersClicked() {
        sendNewEffect(OverviewUiEffect.ViewMoreUsers)
    }

    override fun onViewMoreRestaurantClicked() {
        sendNewEffect(OverviewUiEffect.ViewMoreRestaurant)
    }

    override fun onViewMoreTaxiClicked() {
        sendNewEffect(OverviewUiEffect.ViewMoreTaxis)
    }

    private fun getLatestRegisteredUsers() {
        tryToExecute(
            { exploreDashboard.getLastRegisteredUsers(USER_NUMBER) },
            ::onGetUsersSuccessfully,
            ::onError
        )
    }

    private fun onGetUsersSuccessfully(users: List<User>) {
        val latestRegisteredUsers = users.toLatestUsersUiState()
        updateState {
            it.copy(users = latestRegisteredUsers, isLoading = false, hasInternetConnection = true)
        }
    }

    private fun onError(error: ErrorState) {
        when (error) {
            is ErrorState.NoConnection -> {
                updateState { it.copy(hasInternetConnection = false) }
            }

            else -> {
                updateState { it.copy(error = error, isLoading = false) }
            }
        }
    }

    companion object {
        private const val USER_NUMBER = 4
    }

}