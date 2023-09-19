package presentation.login

import presentation.base.BaseInteractionListener

interface LoginScreenInteractionListener : BaseInteractionListener {
    //login
    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
    fun onKeepLoggedInClicked()
    fun onClickLogin(userName: String, password: String, isKeepMeLoggedInChecked: Boolean)

    //permission will move
    fun onOwnerEmailChanged(ownerEmail: String)
    fun onDriverFullNameChanged(driverFullName: String)
    fun onDescriptionChanged(description: String)
    fun onRequestPermissionClicked()
    fun onSubmitClicked(driverFullName: String, driverEmail: String, description: String)
    fun onDismissSheet()
}