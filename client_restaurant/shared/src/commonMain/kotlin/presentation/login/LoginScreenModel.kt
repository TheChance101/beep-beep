package presentation.login

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class LoginScreenModel :
    BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
    LoginScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    override fun onEmailChanged(email: String) {
        updateState { it.copy(email = email) }
    }

    override fun onOwnerEmailChanged(ownerEmail: String) {
        updateState { it.copy(ownerEmail = ownerEmail) }
    }

    override fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password) }
    }

    override fun onNameChanged(restaurantName: String) {
        updateState { it.copy(restaurantName = restaurantName) }
    }

    override fun onDescriptionChanged(description: String) {
        updateState { it.copy(description = description) }
    }

    override fun onClickLogin(ownerId: String) {
        tryToExecute(
            onSuccess = {
//                 sendNewEffect(LoginScreenUIEffect.LoginEffect(ownerId))
            },
            onError = {
                state.value.sheetState.show()
            },
            function = {}
        )
    }

    override fun onRequestPermissionClick() {
        updateState { it.copy(showPermissionSheet = true) }
    }

    override fun onClickSubmit() {
        // TODO: send permission request
        state.value.sheetState.dismiss()
        updateState { it.copy(showPermissionSheet = false) }
    }

    override fun onCancelClick() {
        updateState { it.copy(showPermissionSheet = false) }
        state.value.sheetState.dismiss()
    }


}
