package presentation.login

import presentation.base.BaseInteractionListener

interface LoginScreenInteractionListener : BaseInteractionListener {
    fun onEmailChanged(email: String)
    fun onOwnerEmailChanged(ownerEmail: String)
    fun onPasswordChanged(password: String)
    fun onNameChanged(restaurantName: String)
    fun onDescriptionChanged(description: String)
    fun onClickLogin(ownerId: String)
    fun onRequestPermissionClick()
    fun onClickSubmit()
    fun onCancelClick()
}