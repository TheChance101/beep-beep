package presentation.login

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.ILoginUserUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class LoginScreenModel(private val loginUserUseCase: ILoginUserUseCase) :
    BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
    LoginScreenInteractionListener {
    init {
        coroutineScope.launch {
            if (loginUserUseCase.getKeepMeLoggedInFlag()) {
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
        updateState { it.copy(isLoading = false, error = error) }
        handleErrorState(error)
    }

    private fun handleErrorState(error: ErrorState) {
        when (error) {
            ErrorState.NoInternet -> {}
            ErrorState.UnAuthorized -> {}
            ErrorState.HasNoPermission -> {
                state.value.sheetState.show()
            }

            is ErrorState.UnknownError -> {}
            is ErrorState.ServerError -> {}
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

            ErrorState.NotFound -> {

            }
        }
    }

    // region permission
    override fun onDriverFullNameChanged(driverFullName: String) {
        updateState { it.copy(driverFullName = driverFullName) }
    }

    override fun onOwnerEmailChanged(ownerEmail: String) {
        updateState { it.copy(driverEmail = ownerEmail) }
    }

    override fun onDescriptionChanged(description: String) {
        updateState { it.copy(description = description) }
    }

    override fun onRequestPermissionClicked() {
        coroutineScope.launch {
            state.value.sheetState.dismiss()
            delay(300)
            updateState { it.copy(showPermissionSheet = true) }
            state.value.sheetState.show()
        }
    }


    override fun onSubmitClicked(
        driverFullName: String,
        driverEmail: String,
        description: String
    ) {
        tryToExecute(
            {
                loginUserUseCase.requestPermission(
                    driverFullName,
                    driverEmail,
                    description
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
