package org.thechance.common.presentation.overview

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.RevenueShare
import org.thechance.common.domain.entity.TotalRevenueShare
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.usecase.IManageRevenueShareUseCase
import org.thechance.common.domain.usecase.IUsersManagementUseCase
import org.thechance.common.domain.util.RevenueShareDate
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState

class OverviewScreenModel(
    private val getUsers: IUsersManagementUseCase,
    private val manageRevenueShare: IManageRevenueShareUseCase
) : BaseScreenModel<OverviewUiState, OverviewUiEffect>(OverviewUiState()),
    OverviewInteractionListener {

    init {
        getLatestRegisteredUsers()
        getRevenueShare(RevenueShareDate.MONTHLY)
        getDashboardRevenueShare()

    }

    private fun getRevenueShare(revenueShareDate: RevenueShareDate) {
        tryToExecute(
            { manageRevenueShare.getRevenueShare(revenueShareDate) },
            ::onGetRevenueShareSuccessfully,
            ::onError
        )
    }

    private fun getDashboardRevenueShare() {
        tryToExecute(
            { manageRevenueShare.getDashboardRevenueShare() },
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
            0 -> getRevenueShare(RevenueShareDate.MONTHLY)
            1 -> getRevenueShare(RevenueShareDate.WEEKLY)
            2 -> getRevenueShare(RevenueShareDate.DAILY)
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
            { getUsers.getLastRegisteredUsers(4) },
            ::onGetUsersSuccessfully,
            ::onError
        )
    }

    private fun onGetUsersSuccessfully(users: List<User>) {
        val latestRegisteredUsers = users.toLatestUsersUiState()
        updateState {
            it.copy(users = latestRegisteredUsers, isLoading = false)
        }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error, isLoading = false) }
    }

}