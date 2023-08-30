package presentation.login

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.ILoginUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class LoginScreenModel:
    BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
    LoginScreenInteractionListener {

    private val loginUserUseCase: ILoginUserUseCase by inject()
    override val viewModelScope: CoroutineScope
        get() = coroutineScope

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
        isKeepMeLoggedInChecked: Boolean
    ) {
        tryToExecute(
            { loginUserUseCase.loginUser(userName, password, isKeepMeLoggedInChecked) },
            { onLoginSuccess() },
            this::onLoginFailed
        )
    }

    private fun onLoginSuccess() {
        updateState {
            it.copy(
                isLoading = false,
                isSuccess = true,
                usernameErrorMsg = "",
                passwordErrorMsg = ""
            )
        }

        sendNewEffect(LoginScreenUIEffect.LoginEffect(""))
    }

    private fun onLoginFailed(error: ErrorState) {
        sendNewEffect(LoginScreenUIEffect.LoginEffect(""))
        updateState { it.copy(isLoading = false, error = error) }
//        handleErrorState(error)
    }

    private fun handleErrorState(error: ErrorState) {
        when (error) {
            ErrorState.NoInternet -> {}
            ErrorState.RequestFailed -> {}
            ErrorState.UnAuthorized -> {}
            ErrorState.HasNoPermission -> {}
            ErrorState.UnknownError -> {}
            is ErrorState.InvalidCredentials -> {
                updateState {
                    it.copy(
                        passwordErrorMsg = error.errorMessage,
                        isPasswordError = true,
                    )
                }


            }

            is ErrorState.UserNotExist -> {
                updateState {
                    it.copy(
                        usernameErrorMsg = error.errorMessage,
                        isUsernameError = true
                    )
                }
            }
        }
    }

    // region permission
    override fun onRestaurantNameChanged(restaurantName: String) {
        updateState { it.copy(restaurantName = restaurantName) }
    }

    override fun onOwnerEmailChanged(ownerEmail: String) {
        updateState { it.copy(ownerEmail = ownerEmail) }
    }

    override fun onDescriptionChanged(description: String) {
        updateState { it.copy(description = description) }
    }

    override fun onRequestPermissionClicked() {
        showPermissionSheetWithDelay()

    }

    private fun showPermissionSheetWithDelay() {
        coroutineScope.launch {
            state.value.sheetState.dismiss()
            delay(300)
            updateState { it.copy(showPermissionSheet = true) }
            state.value.sheetState.show()
        }
    }

    override fun onSubmitClicked() {
        state.value.sheetState.dismiss()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
    }

    override fun onCancelClicked() {
        state.value.sheetState.dismiss()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
    }

    override fun onSheetBackgroundClicked() {
        state.value.sheetState.dismiss()
        coroutineScope.launch {
            delayAndChangePermissionSheetState(false)
        }
    }

    private suspend fun delayAndChangePermissionSheetState(show: Boolean) {
        delay(300)
        updateState { it.copy(showPermissionSheet = show) }
    }
    // endregion
}
