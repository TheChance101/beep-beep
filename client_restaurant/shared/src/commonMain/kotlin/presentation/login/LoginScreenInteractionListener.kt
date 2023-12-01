package presentation.login

import presentation.base.BaseInteractionListener

interface LoginScreenInteractionListener : BaseInteractionListener {
    //login
    fun onUserNameChanged(userName: String)
    fun onPasswordChanged(password: String)
    fun onKeepLoggedInClicked()
    fun onClickLogin(userName: String, password: String, isKeepMeLoggedInChecked: Boolean)

    //permission will move
    fun onOwnerEmailChanged(ownerEmail: String)
    fun onRestaurantNameChanged(restaurantName: String)
    fun onDescriptionChanged(description: String)
    fun onRequestPermissionClicked()
    fun onDismissSnackBar()
    fun onSubmitClicked(restaurantName: String, ownerEmail: String, description: String)
    fun onCancelClicked()
    fun onSheetBackgroundClicked()
}