package org.thechance.common.presentation.login

import org.thechance.common.domain.usecase.ILoginUserUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.restaurant.ErrorWrapper
import org.thechance.common.presentation.util.ErrorState


class LoginScreenModel(
    private val login: ILoginUserUseCase
) : BaseScreenModel<LoginUIState, LoginUIEffect>(LoginUIState()), LoginInteractionListener {

    override fun onPasswordChange(password: String) {
        updateState { it.copy(password = password) }
    }

    override fun onUsernameChange(username: String) {
        updateState { it.copy(username = username) }
    }

    override fun onLoginClicked() {
        val currentState = mutableState.value
        clearErrorState()
        tryToExecute(
            {
                login.loginUser(
                    username = currentState.username,
                    password = currentState.password,
                    keepLoggedIn = currentState.keepLoggedIn
                )
            },
            onSuccess = { onLoginSuccess() },
            onError = ::onLoginError
        )
    }

    override fun onKeepLoggedInClicked() {
        updateState { it.copy(keepLoggedIn = !it.keepLoggedIn) }
    }

    private fun onLoginSuccess() {
        updateState { it.copy(isLoading = false, error = null) }
        sendNewEffect(LoginUIEffect.LoginSuccess)
    }

    private fun onLoginError(error: ErrorState) {
        updateState { it.copy(isLoading = false, error = error) }
        handleErrorState(error)
    }

    private fun handleErrorState(error: ErrorState) {
        when (error) {
            is ErrorState.InvalidPassword -> {
                updateState { it.copy(passwordError = ErrorWrapper(error.errorMessage,true)) }
                sendNewEffect(LoginUIEffect.LoginFailed(error.errorMessage))
            }

            is ErrorState.UserNotExist -> {
                updateState { it.copy(usernameError = ErrorWrapper(error.errorMessage,true)) }
                sendNewEffect(LoginUIEffect.LoginFailed(error.errorMessage))
            }

            ErrorState.NoConnection -> {

            }

            ErrorState.UnKnownError -> {

            }

            else -> {
                sendNewEffect(LoginUIEffect.LoginFailed("Unknown error"))
            }
        }
    }

    private fun clearErrorState() =
        updateState {
            it.copy(
                error = null,
                usernameError = ErrorWrapper("name must be longer than 2",false),
                passwordError = ErrorWrapper("password must contains letter and number",false),
                isLoading = false
            )
        }

}