package org.thechance.common.presentation.overview

import org.thechance.common.presentation.base.BaseInteractionListener

interface OverviewInteractionListener : BaseInteractionListener {

    fun onMenuItemDropDownClicked()

    fun onMenuItemClicked(index: Int)

    fun onDismissDropDownMenu()

    fun onViewMoreUsersClicked()

    fun onViewMoreRestaurantClicked()

    fun onViewMoreTaxiClicked()

    fun onRetry()

}