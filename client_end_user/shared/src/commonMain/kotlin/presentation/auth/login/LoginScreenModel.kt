package presentation.auth.login

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageAuthenticationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class LoginScreenModel(
    private val manageAuthentication: IManageAuthenticationUseCase,
) : BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
    LoginScreenInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope

    override fun onUsernameChanged(username: String) {
        updateState { it.copy(username = username) }
    }

    override fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password) }
    }

    override fun onKeepLoggedInChecked() {
        updateState { it.copy(keepLoggedIn = !it.keepLoggedIn) }
    }

    override fun onDismissSnackBar() {
        updateState { it.copy(showSnackbar = false,snackbarMessage = "") }
    }

    override fun onClickLogin(username: String, password: String, keepLoggedIn: Boolean) {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { manageAuthentication.loginUser(username, password, keepLoggedIn) },
            ::onLoginSuccess,
            ::onLoginError
        )
    }

    private fun onLoginSuccess(isLoggedIn: Boolean) {
        clearErrors()
        if (isLoggedIn) {
            sendNewEffect(LoginScreenUIEffect.NavigateToHome)
        }
    }

    private fun onLoginError(errorState: ErrorState) {
        clearErrors()
        when (errorState) {
            ErrorState.InvalidPassword -> updateState { it.copy(isPasswordError = true) }

            ErrorState.InvalidUsername -> updateState { it.copy(isUsernameError = true) }

            is ErrorState.WrongPassword -> {
                updateState { it.copy(snackbarMessage = errorState.message, showSnackbar = true) }
            }

            is ErrorState.UserNotFound -> {
                updateState { it.copy(snackbarMessage = errorState.message, showSnackbar = true) }

            }

            else -> {}
        }
    }

    private fun clearErrors() {
        updateState {
            it.copy(
                isUsernameError = false,
                isPasswordError = false,
                isLoading = false
            )
        }
    }

    override fun onClickSignUp() {
        sendNewEffect(LoginScreenUIEffect.NavigateToSignup)
    }

}
