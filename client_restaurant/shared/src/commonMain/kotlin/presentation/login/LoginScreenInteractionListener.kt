package presentation.login

import presentation.base.BaseInteractionListener

interface LoginScreenInteractionListener : BaseInteractionListener {
    fun onEmailChanged(email: String)
    fun onOwnerEmailChanged(email: String)
    fun onPasswordChanged(password: String)
    fun onNameChanged(username: String)
    fun onDescriptionChanged(description: String)
    fun onClickLogin()
    fun onClickSubmit()
    fun onClickCancel()
}