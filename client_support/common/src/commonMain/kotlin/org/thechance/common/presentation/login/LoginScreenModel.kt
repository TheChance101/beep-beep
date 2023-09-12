package org.thechance.common.presentation.login

import org.thechance.common.presentation.base.BaseScreenModel

class LoginScreenModel : BaseScreenModel<LoginUiState, LoginUIEffect>(LoginUiState()), LoginInteractionListener {

    override fun onPasswordChange(password: String) {
        TODO("Not yet implemented")
    }

    override fun onUsernameChange(username: String) {
        TODO("Not yet implemented")
    }

    override fun onLoginClicked() {
        TODO("Not yet implemented")
    }

    override fun onKeepLoggedInClicked() {
        TODO("Not yet implemented")
    }

}