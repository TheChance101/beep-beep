package presentation.login

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.DeliveryRequestPermission
import domain.usecase.IManageLoginUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class LoginScreenModel(private val manageLoginUser: IManageLoginUserUseCase) :
    BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
    LoginScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = coroutineScope

    init {
        loginIfKeepMeLoggedInFlagSet()
    }

    private fun loginIfKeepMeLoggedInFlagSet(){
        viewModelScope.launch {
            if (manageLoginUser.getKeepMeLoggedInFlag()) {
                sendNewEffect(LoginScreenUIEffect.LoginEffect(""))
            }
        }
    }

    override fun onUserNameChanged(userName: String) {
        updateState { it.copy(userName = userName) }
    }

    override fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password) }
    }

    override fun onKeepLoggedInClicked() {
        updateState { it.copy(keepLoggedIn = !it.keepLoggedIn) }
    }

    override fun onClickLogin(
        username: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean
    ) {
        updateState { it.copy(isLoading = true) }
        clearErrors()
        tryToExecute(
            { manageLoginUser.loginUser(username, password, isKeepMeLoggedInChecked) },
            {onLoginSuccess()},
            ::onLoginError
        )
    }

    private fun onLoginSuccess() {
        clearErrors()
        sendNewEffect(LoginScreenUIEffect.LoginEffect(""))
    }

    private fun onLoginError(errorState: ErrorState) {
        clearErrors()
        when (errorState) {
            ErrorState.InvalidPassword -> updateState {
                it.copy(
                    passwordErrorMsg = "Invalid password",
                    isPasswordError = true
                )
            }

            ErrorState.InvalidUsername -> updateState {
                it.copy(
                    usernameErrorMsg = "Invalid username",
                    isUsernameError = true
                )
            }

            is ErrorState.UserNotFound -> showSnackBar("Sign up with Beep Beep account")

            is ErrorState.UnAuthorized -> state.value.sheetState.show()
            else -> {}
        }
    }

    private fun showSnackBar(message: String) {
        viewModelScope.launch {
            updateState { it.copy(snackBarMessage = message, showSnackBar = true) }
            delay(4000) // wait for snack-bar to show
            updateState { it.copy(showSnackBar = false) }
        }
    }


    private fun clearErrors() {
        updateState {
            it.copy(
                usernameErrorMsg = "",
                isUsernameError = false,
                passwordErrorMsg = "",
                isPasswordError = false,
                isLoading = false
            )
        }
    }

    //region permission
    override fun onOwnerEmailChanged(ownerEmail: String) {
        updateState { it.copy(ownerEmail = ownerEmail) }
    }

    override fun onRestaurantNameChanged(restaurantName: String) {
        updateState { it.copy(deliveryUsername = restaurantName) }
    }

    override fun onDescriptionChanged(description: String) {
        updateState { it.copy(description = description) }
    }

    override fun onRequestPermissionClicked() {
        dismissBottomSheet()
        showPermissionSheetWithDelay()
    }

    private fun showPermissionSheetWithDelay() {
        coroutineScope.launch {
            delay(300)
            updateState { it.copy(showPermissionSheet = true) }
            state.value.sheetState.show()
        }
    }

    override fun onSubmitClicked(restaurantName: String, ownerEmail: String, description: String) {
        sendNewEffect(LoginScreenUIEffect.LoginEffect(""))//fake scenario just 4 testing
        val requestPermission = DeliveryRequestPermission(restaurantName, ownerEmail, description)
        tryToExecute(
            {
                manageLoginUser.requestPermission(
                    requestPermission
                )
            },
            { onAskForPermissionSuccess() },
            ::onAskForPermissionFailed
        )
    }

    private fun onAskForPermissionSuccess() {
        state.value.sheetState.dismiss()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
        // todo send effect for that to show toast or something
    }


    private fun onAskForPermissionFailed(error: ErrorState) {
        state.value.sheetState.dismiss()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
        // todo send effect for that to show toast or something
    }

    override fun onCancelClicked() {
        dismissBottomSheet()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
    }

    override fun onSheetBackgroundClicked() {
        dismissBottomSheet()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
    }

    private fun dismissBottomSheet() {
        state.value.sheetState.dismiss()
    }

    private suspend fun delayAndChangePermissionSheetState(show: Boolean) {
        delay(300)
        updateState { it.copy(showPermissionSheet = show) }
    }
    //end region
}