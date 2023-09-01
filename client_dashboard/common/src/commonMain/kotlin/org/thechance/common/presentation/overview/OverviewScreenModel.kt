package org.thechance.common.presentation.overview

import org.thechance.common.domain.entity.User
import org.thechance.common.domain.usecase.IGetLatestRegisteredUsersUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState

class OverviewScreenModel(
    private val getLatestRegisteredUsers: IGetLatestRegisteredUsersUseCase
) : BaseScreenModel<OverviewUiState, OverviewUiEffect>(OverviewUiState()),
    OverviewInteractionListener {

    init {
        getUsers()
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

    fun getUsers() {
        tryToExecute(
            { getLatestRegisteredUsers.getLatestRegisteredUsers() },
            ::onGetUsersSuccessfully,
            ::onError
        )
    }

    private fun onGetUsersSuccessfully(users: List<User>) {
        val latestRegisteredUsers = users.map { it.toUiState() }
        updateState {
            it.copy(users = latestRegisteredUsers, isLoading = false)
        }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error, isLoading = false) }
    }

}