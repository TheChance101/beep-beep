package org.thechance.common.presentation.login

import org.thechance.common.presentation.base.BaseInteractionListener

interface LoginInteractionListener : BaseInteractionListener {

    fun onPasswordChange(password: String)

    fun onUsernameChange(username: String)

    fun onLoginClicked()

    fun onKeepLoggedInClicked()

}