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
        updateState { it.copy(isLoading = true) }
        clearErrorState()
        mutableState.value.apply {
            tryToExecute(
                    { login.loginUser(username = username, password = password) },
                    { onLoginSuccess() },
                    ::onError
            )
        }

    }

    private fun onLoginSuccess() {
        updateState { it.copy(isLoading = false, hasInternetConnection = true) }
        sendNewEffect(LoginUIEffect.LoginSuccess)
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(isLoading = false) }
        handleErrorState(error)
    }

    private fun handleErrorState(error: ErrorState) {
        when (error) {
            is ErrorState.MultipleErrors -> {
                val errorStates = error.errors
                updateState {
                    it.copy(
                        isPasswordError = errorStates.firstInstanceOfOrNull<ErrorState.InvalidPassword>()
                            ?.let { error ->
                                ErrorWrapper(error.errorMessage, true)
                            },
                        isUserError = errorStates.firstInstanceOfOrNull<ErrorState.InvalidUserName>()?.let { error ->
                            ErrorWrapper(error.errorMessage, true)
                        }
                    )
                }
            }

            is ErrorState.InvalidPassword -> {
                updateState {
                    it.copy(
                        isPasswordError = ErrorWrapper(error.errorMessage, true)
                    )
                }
            }

            is ErrorState.UserNotExist -> {
                updateState {
                    it.copy(
                        isUserError = ErrorWrapper(error.errorMessage, true)
                    )
                }
            }

            ErrorState.NoConnection -> {
                updateState { it.copy(hasInternetConnection = false) }
            }

            else -> {
                updateState { it.copy(hasInternetConnection = false) }
            }
        }
    }

    override fun onSnackBarDismiss() {
        updateState { it.copy(hasInternetConnection = true) }
    }

    private fun clearErrorState() =
        updateState {
            it.copy(
                isLoading = false,
                isPasswordError = ErrorWrapper(),
                isUserError = ErrorWrapper()
            )
        }
}