package presentation.login

import presentation.base.BaseInteractionListener

interface LoginScreenInteractionListener : BaseInteractionListener, LoginScreenBottomSheetInteractionListener {
    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
    fun onKeepLoggedInClicked()
    fun onClickLogin(userName: String, password: String, isKeepMeLoggedInChecked: Boolean)

}
interface LoginScreenBottomSheetInteractionListener{
    fun onOwnerEmailChanged(ownerEmail: String)
    fun onDriverFullNameChanged(driverFullName: String)
    fun onDescriptionChanged(description: String)
    fun onRequestPermissionClicked()
    fun onSubmitClicked(driverFullName: String, driverEmail: String, description: String)
    fun onDismissSheet()
}