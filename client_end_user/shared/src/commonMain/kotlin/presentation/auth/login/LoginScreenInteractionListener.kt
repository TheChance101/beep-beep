package presentation.auth.login

import presentation.base.BaseInteractionListener

interface LoginScreenInteractionListener : BaseInteractionListener {
    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
    fun onKeepLoggedInChecked()
    fun onClickLogin(username: String, password: String, keepLoggedIn: Boolean)
    fun onClickSignUp()
}