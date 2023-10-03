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
                showSnackbar(errorState.message)
            }

            is ErrorState.UserNotFound -> {
                showSnackbar(errorState.message)
            }

            else -> {}
        }
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            updateState { it.copy(snackbarMessage = message, showSnackbar = true) }
            delay(2000) // wait for snackbar to show
            updateState { it.copy(showSnackbar = false) }
            delay(300) // wait for snackbar to hide
            updateState { it.copy(snackbarMessage = "") }
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
