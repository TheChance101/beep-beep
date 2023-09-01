package presentation.login

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageAuthenticationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class LoginScreenModel(private val manageAuthentication: IManageAuthenticationUseCase) :
    BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
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
        tryToExecute(
            { manageAuthentication.loginUser(username, password) },
            ::onLoginSuccess,
            ::onLoginError
        )
    }

    private fun onLoginSuccess(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            sendNewEffect(LoginScreenUIEffect.Login)
            updateState {
                it.copy(
                    passwordErrorMsg = "",
                    usernameErrorMsg = "",
                    isPasswordError = false,
                    isUsernameError = false
                )
            }
        }
    }

    private fun onLoginError(errorState: ErrorState) {
        println("Ahmed $errorState")
        when (errorState) {
            ErrorState.InvalidPassword -> updateState {
                it.copy(
                    passwordErrorMsg = "invalid password",
                    isPasswordError = true
                )
            }

            ErrorState.InvalidUsername -> updateState {
                it.copy(
                    usernameErrorMsg = "invalid username",
                    isUsernameError = true
                )
            }

            ErrorState.NetworkNotSupported -> {}
            ErrorState.NoInternet -> {}
            ErrorState.RequestFailed -> {}
            ErrorState.UnAuthorized -> updateState {
                it.copy(
                    usernameErrorMsg = "this user is not existed",
                    isUsernameError = true
                )
            }

            ErrorState.WifiDisabled -> {}
        }
    }

    override fun onClickSignUp() {
        // should send effect to navigate to sign up screen
    }

}
