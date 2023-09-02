package org.thechance.common.presentation.main

import org.thechance.common.presentation.base.BaseInteractionListener

interface MainInteractionListener : BaseInteractionListener {

    fun onClickDropDownMenu()
    fun onDismissDropDownMenu()
    fun onClickLogout()
    fun onSwitchTheme()
}