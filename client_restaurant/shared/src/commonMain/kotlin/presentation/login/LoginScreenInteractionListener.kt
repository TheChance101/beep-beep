package presentation.login

import presentation.base.BaseInteractionListener

interface LoginScreenInteractionListener : BaseInteractionListener {
    fun onEmailChanged(email: String)
    fun onPasswordChanged(password: String)
    fun onClickLogin()
    fun onClickCancel()
}