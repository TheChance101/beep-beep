package presentation.login

import presentation.base.BaseInteractionListener

interface LoginScreenInteractionListener: BaseInteractionListener {
    fun onUserNameChanged(userName: String)
    fun onPasswordChanged(password: String)
    fun onKeepLoggedInClicked()
    fun onClickLogin(userName: String, password: String, isKeepMeLoggedInChecked: Boolean)
}