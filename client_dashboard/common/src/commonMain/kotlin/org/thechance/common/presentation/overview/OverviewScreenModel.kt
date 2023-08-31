package org.thechance.common.presentation.overview

import org.thechance.common.presentation.base.BaseScreenModel

class OverviewScreenModel : BaseScreenModel<OverviewUiState, OverviewUiEffect>(OverviewUiState()),
    OverviewInteractionListener {

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

}