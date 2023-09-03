package org.thechance.common.presentation.overview

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.usecase.IManageUsersUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState

class OverviewScreenModel(
    private val manageUsers: IManageUsersUseCase
) : BaseScreenModel<OverviewUiState, OverviewUiEffect>(OverviewUiState()),
    OverviewInteractionListener {

    init {
        getLatestRegisteredUsers()
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
                    isExpanded = false,
                    selectedIndex = index
                )
            )
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
            {
                manageUsers.getUsers(
                    byPermissions = listOf(),
                    byCountries = listOf(),
                    page = 1,
                    numberOfUsers = 4
                )
            },
            ::onGetUsersSuccessfully,
            ::onError
        )
    }

    private fun onGetUsersSuccessfully(users: DataWrapper<User>) {
        val latestRegisteredUsers = users.result.toUiState()
        updateState {
            it.copy(users = latestRegisteredUsers, isLoading = false)
        }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error, isLoading = false) }
    }

}