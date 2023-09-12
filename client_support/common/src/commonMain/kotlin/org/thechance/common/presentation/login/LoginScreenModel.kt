package org.thechance.common.presentation.login

import org.thechance.common.domain.usecase.ILoginUserUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState

class LoginScreenModel(private val loginUserUseCase: ILoginUserUseCase) :
    BaseScreenModel<LoginUIState, LoginUIEffect>(LoginUIState()), LoginInteractionListener {

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
                loginUserUseCase.loginUser(
                    username = currentState.username,
                    password = currentState.password,
                    keepLoggedIn = currentState.keepLoggedIn
                )
            },
            onSuccess = { onLoginSuccess() },
            onError = ::onError
        )
    }

    override fun onKeepLoggedInClicked() {
        updateState { it.copy(keepLoggedIn = !it.keepLoggedIn) }
    }

    private fun clearErrorState() {
        updateState { it.copy(error = null, isLoading = false) }
    }

    private fun onLoginSuccess() {
        updateState { it.copy(isLoading = false, error = null) }
        sendNewEffect(LoginUIEffect.LoginSuccess)
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(isLoading = false, error = error) }
        handleErrorState(error)
    }

    private fun handleErrorState(error: ErrorState) {
        when (error) {
            is ErrorState.InvalidCredentials -> {
                sendNewEffect(LoginUIEffect.LoginFailed(error.errorMessage))
            }

            is ErrorState.UserNotExist -> {
                sendNewEffect(LoginUIEffect.LoginFailed(error.errorMessage))
            }

            ErrorState.NoConnection -> {

            }

            ErrorState.UnKnownError -> {

            }
        }
    }

}