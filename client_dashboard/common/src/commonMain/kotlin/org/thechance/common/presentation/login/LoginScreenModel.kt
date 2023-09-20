package org.thechance.common.presentation.login

import org.thechance.common.domain.usecase.ILoginUserUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.restaurant.ErrorWrapper
import org.thechance.common.presentation.util.ErrorState


class LoginScreenModel(
    private val login: ILoginUserUseCase
) : BaseScreenModel<LoginUIState, LoginUIEffect>(LoginUIState()), LoginInteractionListener {

    override fun onPasswordChange(password: String) {
        updateState { it.copy(password = password, isAbleToLogin = password.isNotBlank()) }
    }

    override fun onUsernameChange(username: String) {
        updateState { it.copy(username = username, isAbleToLogin = username.isNotBlank()) }
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
            onError = ::onError
        )
    }

    override fun onKeepLoggedInClicked() {
        updateState { it.copy(keepLoggedIn = !it.keepLoggedIn) }
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
        updateState { it.copy(isLoading = false) }
        when (error) {
            is ErrorState.MultipleErrors -> {
                val errorStates = error.errors
                updateState {
                    it.copy(
                        isPasswordError = errorStates.firstInstanceOfOrNull<ErrorState.InvalidPassword>()?.let {error ->
                            ErrorWrapper(error.errorMessage, true)
                        },
                        isUserError = errorStates.firstInstanceOfOrNull<ErrorState.InvalidUserName>()?.let {error ->
                            ErrorWrapper(error.errorMessage, true)
                        }
                    )
                }
            }
            else -> {}

        }
    }

    private fun clearErrorState() =
        updateState {
            it.copy(
                error = null,
                isLoading = false,
                isPasswordError = ErrorWrapper(),
                isUserError = ErrorWrapper()
            )
        }
}