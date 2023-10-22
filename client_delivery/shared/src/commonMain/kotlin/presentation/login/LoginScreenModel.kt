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
        showSnackBar()
    }
    private fun showSnackBar() {
        viewModelScope.launch {
            updateState { it.copy(showSnackBar = true) }
            delay(3000)
            updateState { it.copy(showSnackBar = false) }
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
        userName: String,
        password: String,
        isKeepMeLoggedInChecked: Boolean,
    ) {

        updateState { it.copy(isLoading = true, isEnable = false) }
        clearErrors()
        tryToExecute(
            {
                manageLoginUser.loginUser(userName, password, isKeepMeLoggedInChecked)
            },
            { onLoginSuccess(userName) },
            ::onLoginError
        )
    }

    private fun onLoginSuccess(username: String) {
        clearErrors()
        viewModelScope.launch {
            manageLoginUser.saveUsername(username)
        }
        sendNewEffect(LoginScreenUIEffect.LoginEffect(""))

    }

    private fun onLoginError(errorState: ErrorState) {
        updateState { it.copy(isLoading = false, isEnable = true) }
        clearErrors()
        when (errorState) {
            ErrorState.InvalidPassword -> updateState { it.copy(isPasswordError = true) }

            ErrorState.InvalidUsername,ErrorState.UserNotFound -> updateState {
                it.copy(isUsernameError = true)
            }

            is ErrorState.UnAuthorized -> state.value.permissionUiState.sheetState.show()
            else -> {}
        }
    }




    private fun clearErrors() {
        updateState {
            it.copy(
                usernameErrorMsg = "",
                isUsernameError = false,
                passwordErrorMsg = "",
                isPasswordError = false
            )
        }
    }

    //region permission
    override fun onOwnerEmailChanged(ownerEmail: String) {

        updateState { it.copy(permissionUiState = it.permissionUiState.copy(ownerEmail = ownerEmail)) }
    }

    override fun onRestaurantNameChanged(restaurantName: String) {
        updateState { it.copy(permissionUiState = it.permissionUiState.copy(deliveryUsername = restaurantName)) }
    }

    override fun onDescriptionChanged(description: String) {
        updateState { it.copy(permissionUiState = it.permissionUiState.copy(description = description)) }
    }

    override fun onRequestPermissionClicked() {
        dismissBottomSheet()
        showPermissionSheetWithDelay()
    }

    private fun showPermissionSheetWithDelay() {
        coroutineScope.launch {
            delay(300)
            updateState { it.copy(permissionUiState = it.permissionUiState.copy(showPermissionSheet = true)) }
            state.value.permissionUiState.sheetState.show()
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
        state.value.permissionUiState.sheetState.dismiss()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
        // todo send effect for that to show toast or something
    }


    private fun onAskForPermissionFailed(error: ErrorState) {
        state.value.permissionUiState.sheetState.dismiss()
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
        state.value.permissionUiState.sheetState.dismiss()
    }

    private suspend fun delayAndChangePermissionSheetState(show: Boolean) {
        delay(300)
        updateState { it.copy(permissionUiState = it.permissionUiState.copy(showPermissionSheet = show)) }
    }
    //end region
}