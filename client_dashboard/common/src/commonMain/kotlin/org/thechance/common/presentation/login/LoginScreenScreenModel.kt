package org.thechance.common.presentation.login

import kotlinx.coroutines.flow.*
import org.thechance.common.domain.entity.UserTokens
import org.thechance.common.domain.usecase.ILoginUserUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState


class LoginScreenScreenModel(
    private val loginUseCase: ILoginUserUseCase
) : BaseScreenModel<LoginUIState, LoginUIEffect>(LoginUIState()), LoginScreenInteractionListener {

    override fun onPasswordChange(password: String) {
        mutableState.update { it.copy(password = password) }
    }

    override fun onUsernameChange(username: String) {
        mutableState.update { it.copy(username = username) }
    }

    override fun onLoginClicked() {
        updateState {
            it.copy(
                error = null,
                usernameError = "",
                isUsernameError = false,
                passwordError = "",
                isPasswordError = false,
            )
        }
        val currentState = mutableState.value
        tryToExecute(
            {
                loginUseCase.loginUser(
                    username = currentState.username,
                    password = currentState.password,
                    keepLoggedIn = currentState.keepLoggedIn
                )
            },
            onSuccess = ::onLoginSuccess,
            onError = ::onLoginError
        )
    }

    override fun onKeepLoggedInClicked() {
        updateState { it.copy(keepLoggedIn = !it.keepLoggedIn) }
    }

    private fun onLoginSuccess(result: UserTokens) {
        updateState { it.copy(isLoading = false, error = null) }
        sendNewEffect(LoginUIEffect.LoginUISuccess)
    }

    private fun onLoginError(error: ErrorState) {
        handleErrorState(error)
        sendNewEffect(LoginUIEffect.LoginUIFailed)
        updateState { it.copy(isLoading = false, error = error) }
    }

    private fun handleErrorState(error: ErrorState) {
        when (error) {
            is ErrorState.InvalidCredentials -> {
                updateState { it.copy(passwordError = error.errorMessage, isPasswordError = true) }
            }

            is ErrorState.UserNotExist -> {
                updateState { it.copy(usernameError = error.errorMessage, isUsernameError = true) }
            }

            ErrorState.NoConnection -> {

            }

            ErrorState.UnKnownError -> {

            }
        }
    }

}